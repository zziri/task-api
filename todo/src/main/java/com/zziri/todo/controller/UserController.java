package com.zziri.todo.controller;

import com.google.gson.Gson;
import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.User;
import com.zziri.todo.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/user")
public class UserController {
    private final UserService userService;
    private final Gson gson;

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @GetMapping
    public Response<User> findUser(@RequestHeader("X-AUTH-TOKEN") String token) {
        return userService.findByToken(token);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    @PatchMapping
    public Response<User> patchUserInfo(@RequestHeader("X-AUTH-TOKEN") String token, @RequestBody String userInfo) {
        User user = gson.fromJson(userInfo, User.class);
        return userService.patchUserInfo(token, user);
    }


//    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
//    @GetMapping(value = "/users")
//    public Response<List<User>> findAllUser() {
//        return userService.findAllUser();
//    }
//
//    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
//    @GetMapping(value = "/user/{id}")
//    public Response<User> findById(@PathVariable Long id) {
//        return userService.findById(id);
//    }
//
//    @PostMapping(value = "/user")
//    public Response<User> post(@RequestParam String account, @RequestParam String name) {
//        return userService.post(account, name);
//    }
//
//    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
//    @PutMapping(value = "/user")
//    public Response<User> modify(@RequestParam long id, @RequestParam String account, @RequestParam String name) {
//        return userService.modify(id, account, name);
//    }
//
//    @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
//    @DeleteMapping(value = "/user")
//    public Response<User> delete(@RequestParam long id) {
//        return userService.delete(id);
//    }
}