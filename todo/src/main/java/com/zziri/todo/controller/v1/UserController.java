package com.zziri.todo.controller.v1;

import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.User;
import com.zziri.todo.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @GetMapping(value = "/users")
    public Response<List<User>> findAllUser() {
        return userService.findAllUser();
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @GetMapping(value = "/user/{id}")
    public Response<User> findById(@RequestParam Long id) {
        return userService.findById(id);
    }

    @PostMapping(value = "/user")
    public Response<User> post(@RequestParam String account, @RequestParam String name) {
        return userService.post(account, name);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @PutMapping(value = "/user")
    public Response<User> modify(@RequestParam long id, @RequestParam String account, @RequestParam String name) {
        return userService.modify(id, account, name);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @DeleteMapping(value = "/user")
    public Response<User> delete(@RequestParam long id) {
        return userService.delete(id);
    }
}