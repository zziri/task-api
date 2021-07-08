package com.zziri.task.service.security;

import com.zziri.task.config.security.JwtTokenProvider;
import com.zziri.task.domain.Response;
import com.zziri.task.domain.SocialProfile;
import com.zziri.task.domain.User;
import com.zziri.task.exception.custom.AccountSigninFailedException;
import com.zziri.task.exception.custom.UserExistException;
import com.zziri.task.exception.custom.UserNotFoundException;
import com.zziri.task.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class SignService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Response<String> signup(String account, String password, String name) {
        synchronized (userRepo) {
            Optional<User> user = userRepo.findByAccount(account);
            if (user.isPresent())
                throw new UserExistException();

            userRepo.save(User.builder()
                    .account(account)
                    .password(passwordEncoder.encode(password))
                    .name(name)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build());
        }

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

    public Response<String> signinBySocial(String social, SocialProfile profile) {
        User user = userRepo.findByAccountAndProvider(profile.getAccount(), social).orElseThrow(UserNotFoundException::new);
        String token = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles());

        return Response.<String>builder()
                .data(token).build();
    }

    public Response<String> signupBySocial(String social, String name, SocialProfile profile) {
        synchronized (userRepo) {
            Optional<User> user = userRepo.findByAccountAndProvider(profile.getAccount(), social);
            if (user.isPresent())
                throw new UserExistException();

            userRepo.save(User.builder()
                    .account(profile.getAccount())
                    .provider(social)
                    .name(name)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build());
        }

        return Response.<String>builder().build();
    }

    public Response<String> auth(String social, SocialProfile profile) {
        try {
            signupBySocial(social, profile.getName(), profile);
        } catch (UserExistException e) {
            return signinBySocial(social, profile);
        }
        return signinBySocial(social, profile);
    }

    public Response<String> auth(String account, String password) {
        try {
            signup(account, password, "");
        } catch (UserExistException e) {
            return signin(account, password);
        }
        return signin(account, password);
    }
}
