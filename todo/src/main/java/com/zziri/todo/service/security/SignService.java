package com.zziri.todo.service.security;

import com.zziri.todo.config.security.JwtTokenProvider;
import com.zziri.todo.domain.KakaoProfile;
import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.User;
import com.zziri.todo.exception.custom.AccountSigninFailedException;
import com.zziri.todo.exception.custom.UserExistException;
import com.zziri.todo.exception.custom.UserNotFoundException;
import com.zziri.todo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class SignService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SignService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Response<String> signup(String account, String password, String name) {
        userRepo.save(User.builder()
                .account(account)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return Response.<String>builder()
                .data("").build();
    }

    public Response<String> signin(String account, String password) {
        User user = userRepo.findByAccount(account).orElseThrow(AccountSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new AccountSigninFailedException();
        String token = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles());

        return Response.<String>builder()
                .data(token).build();
    }

    public Response<String> signinByProvider(String provider, KakaoProfile profile) {
        User user = userRepo.findByAccountAndProvider(String.valueOf(profile.getId()), provider).orElseThrow(UserNotFoundException::new);
        String token = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles());
        return Response.<String>builder()
                .data(token).build();
    }

    public Response<String> signupProvider(String provider, String name, KakaoProfile profile) {
        Optional<User> user = userRepo.findByAccountAndProvider(String.valueOf(profile.getId()), provider);
        if(user.isPresent())
            throw new UserExistException();

        userRepo.save(User.builder()
                .account(String.valueOf(profile.getId()))
                .provider(provider)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return Response.<String>builder().build();
    }
}
