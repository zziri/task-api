package com.zziri.todo.controller.v1;

import com.zziri.todo.config.security.JwtTokenProvider;
import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.TodoTask;
import com.zziri.todo.service.TodoTaskService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/todo/task")
public class TodoController {
    private final JwtTokenProvider jwtTokenProvider;
    private final TodoTaskService todoTaskService;

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TodoTask> addTodoTask(@RequestParam String task, @RequestHeader("X-AUTH-TOKEN") String token) {
        String userPk = jwtTokenProvider.getUserPk(token);
        return todoTaskService.addTodoTask(task, Long.valueOf(userPk));
    }
}
