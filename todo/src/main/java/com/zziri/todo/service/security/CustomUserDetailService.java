package com.zziri.todo.service.security;

import com.zziri.todo.exception.custom.UserNotFoundException;
import com.zziri.todo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userPk) {
        return (UserDetails) userRepo.findById(Long.valueOf(userPk)).orElseThrow(UserNotFoundException::new);
    }
}
