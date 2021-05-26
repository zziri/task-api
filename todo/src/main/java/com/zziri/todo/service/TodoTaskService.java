package com.zziri.todo.service;

import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.TodoTask;
import com.zziri.todo.exception.custom.TaskNotFoundException;
import com.zziri.todo.repository.TodoTaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoTaskService {
    private final TodoTaskRepo todoTaskRepo;

    public Response<TodoTask> addTodoTask(TodoTask todoTask, Long userPk) {
        todoTask.setOwnerId(userPk);
        todoTask = todoTaskRepo.save(todoTask);
        return Response.<TodoTask>builder()
                .data(todoTask).build();
    }

    public Response<List<TodoTask>> getTodoTasks(Long userPk) {
        List<TodoTask> todoTasks = todoTaskRepo.findByOwnerId(userPk);
        return Response.<List<TodoTask>>builder()
                .data(todoTasks).build();
    }

    public Response<TodoTask> patchTodoTask(TodoTask input, Long taskId, Long userPk) {
        TodoTask origin = todoTaskRepo.findByOwnerIdAndId(userPk, taskId).orElseThrow(TaskNotFoundException::new);
        origin.patch(input);
        return Response.<TodoTask>builder()
                .data(todoTaskRepo.save(origin)).build();
    }

    public Response<Object> deleteTodoTask(Long taskId, Long userPk) {
        TodoTask toBeDeleted = todoTaskRepo.findByOwnerIdAndId(userPk, taskId).orElseThrow(TaskNotFoundException::new);
        todoTaskRepo.delete(toBeDeleted);
        return Response.builder().build();
    }
}
