package com.zziri.task.service;

import com.zziri.task.config.security.JwtTokenProvider;
import com.zziri.task.domain.Response;
import com.zziri.task.domain.User;
import com.zziri.task.exception.custom.UserNotFoundException;
import com.zziri.task.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;
    private final JwtTokenProvider jwtTokenProvider;

    public Response<User> findById(Long id) {
        User user = userRepo.findById(id).orElseThrow(UserNotFoundException::new);

        return Response.<User>builder()
                .data(user).build();
    }

    public Response<User> findByToken(String token) {
        String userPk = jwtTokenProvider.getUserPk(token);
        return findById(Long.valueOf(userPk));
    }

    public Response<User> patchUserInfo(String token, User userInfo) {
        String userPk = jwtTokenProvider.getUserPk(token);
        User user = userRepo.findById(Long.valueOf(userPk)).orElseThrow(UserNotFoundException::new);
        user.patch(userInfo);
        user = userRepo.save(user);
        return Response.<User>builder()
                .data(user).build();
    }

    public Response<List<User>> findAllUser() {
        return Response.<List<User>>builder()
                .data(userRepo.findAll()).build();
    }

    public Response<User> post(String account, String name) {
        User user = User.builder()
                .account(account)
                .name(name).build();
        userRepo.save(user);

        return Response.<User>builder()
                .data(user).build();
    }

    public Response<User> modify(long id, String account, String name) {
        User user = userRepo.findById(id).orElseThrow(UserNotFoundException::new);
        user.setAccount(account);
        user.setName(name);

        return Response.<User>builder()
                .data(userRepo.save(user)).build();
    }

    public Response<User> delete(long id) {
        User user = userRepo.findById(id).orElseThrow(UserNotFoundException::new);
        userRepo.delete(user);

        return Response.<User>builder()
                .data(user).build();
    }
}
