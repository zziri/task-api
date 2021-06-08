package com.zziri.task.service.security;

import com.zziri.task.exception.custom.UserNotFoundException;
import com.zziri.task.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepo userRepo;

    @Autowired
    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userPk) {
        return userRepo.findById(Long.valueOf(userPk)).orElseThrow(UserNotFoundException::new);
    }
}
