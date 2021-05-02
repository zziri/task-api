package com.zziri.todo.service;

import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.User;
import com.zziri.todo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Response<User> findById(Long id) {
        User user = userRepo.findById(id).orElseThrow(RuntimeException::new);
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
}
