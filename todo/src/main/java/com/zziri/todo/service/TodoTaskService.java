package com.zziri.todo.service;

import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.TodoTask;
import com.zziri.todo.repository.TodoTaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoTaskService {
    private final TodoTaskRepo todoTaskRepo;

    public Response<TodoTask> addTodoTask(String task, Long userPk) {
        TodoTask todoTask = TodoTask.builder()
                .title(task)
                .ownerId(userPk).build();
        todoTask = todoTaskRepo.save(todoTask);
        todoTask.setId(todoTask.getPk().toString());
        todoTask = todoTaskRepo.save(todoTask);
        return Response.<TodoTask>builder()
                .data(todoTask).build();
    }
}
