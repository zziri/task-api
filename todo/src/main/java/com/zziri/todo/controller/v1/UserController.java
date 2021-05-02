package com.zziri.todo.controller.v1;

import com.zziri.todo.domain.User;
import com.zziri.todo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<User> findAllUser() {
        return userRepo.findAll();
    }

    @PostMapping
    public User save() {
        User user = User.builder()
                .account("jihzang@naver.com")
                .name("jihoon")
                .build();
        return userRepo.save(user);
    }
}
