package com.zziri.todo.service;

import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.TodoTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoTaskServiceTest {
    @Autowired
    private TodoTaskService todoTaskService;
    @Test
    public void addTodoTask() {
        Response<TodoTask> ret = todoTaskService.addTodoTask(TodoTask.builder().title("task").build(), 1L);
        System.out.println();
    }
}