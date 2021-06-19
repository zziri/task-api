package com.zziri.task.controller;

import com.google.gson.*;
import com.zziri.task.config.security.JwtTokenProvider;
import com.zziri.task.domain.Response;
import com.zziri.task.domain.TodoTask;
import com.zziri.task.service.TodoTaskService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v2/tasks")
public class TaskController {
    private final JwtTokenProvider jwtTokenProvider;
    private final TodoTaskService todoTaskService;
    private final Gson gson;

    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TodoTask> addTodoTask(@RequestBody String json, @RequestHeader("Task-Authentication") String token) {
        String userPk = jwtTokenProvider.getUserPk(token);
        TodoTask todoTask = gson.fromJson(json, TodoTask.class);
        return todoTaskService.addTodoTask(todoTask, Long.valueOf(userPk));
    }

    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<List<TodoTask>> getTodoTasks(@RequestHeader("Task-Authentication") String token) {
        String userPk = jwtTokenProvider.getUserPk(token);
        return todoTaskService.getTodoTasks(Long.valueOf(userPk));
    }

    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
    @PatchMapping(value = "/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<TodoTask> patchTodoTask(@RequestBody String json, @RequestHeader("Task-Authentication") String token, @PathVariable Long taskId) {
        String userPk = jwtTokenProvider.getUserPk(token);
        TodoTask todoTask = gson.fromJson(json, TodoTask.class);
        return todoTaskService.patchTodoTask(todoTask, taskId, Long.valueOf(userPk));
    }

    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
    @DeleteMapping(value = "/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> deleteTodoTask(@RequestHeader("Task-Authentication") String token, @PathVariable Long taskId) {
        String userPk = jwtTokenProvider.getUserPk(token);
        return todoTaskService.deleteTodoTask(taskId, Long.valueOf(userPk));
    }

    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
    @PostMapping(value = "/diff")
    @ResponseStatus(HttpStatus.OK)
    public Response<List<TodoTask>> getTodoTasksDiff(@RequestHeader("Task-Authentication") String token, @RequestBody String json) {
        String userPk = jwtTokenProvider.getUserPk(token);
        TodoTask[] tasks = gson.fromJson(json, TodoTask[].class);
        return todoTaskService.getTodoTasksDiff(Long.valueOf(userPk), new ArrayList<>(Arrays.asList(tasks)));
    }
}
