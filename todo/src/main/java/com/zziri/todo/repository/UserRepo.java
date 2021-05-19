package com.zziri.todo.repository;

import com.zziri.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByAccount(String account);

    Optional<User> findByAccountAndProvider(String valueOf, String provider);
}
