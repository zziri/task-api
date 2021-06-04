package com.zziri.todo.controller;

import com.google.gson.Gson;
import com.zziri.todo.config.security.JwtTokenProvider;
import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.TodoTask;
import com.zziri.todo.service.TodoTaskService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/task")
public class TodoController {
    private final JwtTokenProvider jwtTokenProvider;
    private final TodoTaskService todoTaskService;
    private final Gson gson;

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TodoTask> addTodoTask(@RequestBody String json, @RequestHeader("X-AUTH-TOKEN") String token) {
        String userPk = jwtTokenProvider.getUserPk(token);
        TodoTask todoTask = gson.fromJson(json, TodoTask.class);
        return todoTaskService.addTodoTask(todoTask, Long.valueOf(userPk));
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<List<TodoTask>> getTodoTasks(@RequestHeader("X-AUTH-TOKEN") String token) {
        String userPk = jwtTokenProvider.getUserPk(token);
        return todoTaskService.getTodoTasks(Long.valueOf(userPk));
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @PatchMapping(value = "/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<TodoTask> patchTodoTask(@RequestBody String json, @RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long taskId) {
        String userPk = jwtTokenProvider.getUserPk(token);
        TodoTask todoTask = gson.fromJson(json, TodoTask.class);
        return todoTaskService.patchTodoTask(todoTask, taskId, Long.valueOf(userPk));
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @DeleteMapping(value = "/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> deleteTodoTask(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long taskId) {
        String userPk = jwtTokenProvider.getUserPk(token);
        return todoTaskService.deleteTodoTask(taskId, Long.valueOf(userPk));
    }
}
