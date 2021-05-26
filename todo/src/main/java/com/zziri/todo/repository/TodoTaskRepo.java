package com.zziri.todo.repository;

import com.zziri.todo.domain.TodoTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoTaskRepo extends JpaRepository<TodoTask, Long> {

}
