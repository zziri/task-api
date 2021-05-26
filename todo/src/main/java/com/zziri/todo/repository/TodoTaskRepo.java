package com.zziri.todo.repository;

import com.zziri.todo.domain.TodoTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoTaskRepo extends JpaRepository<TodoTask, Long> {
    List<TodoTask> findByOwnerId(Long ownerId);
    Optional<TodoTask> findByOwnerIdAndId(Long ownerId, Long id);
}
