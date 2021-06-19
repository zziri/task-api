package com.zziri.task.controller;

import com.google.gson.Gson;
import com.zziri.task.domain.Response;
import com.zziri.task.domain.User;
import com.zziri.task.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/user")
@Slf4j
public class UserController {
    private final UserService userService;
    private final Gson gson;

    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
    @GetMapping
    public Response<User> findUser(@RequestHeader("Task-Authentication") String token, HttpServletRequest request) {
        log.info("[jihoon]" + getRemoteIp(request));
        return userService.findByToken(token);
    }

    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
    @PatchMapping
    public Response<User> patchUserInfo(@RequestHeader("Task-Authentication") String token, @RequestBody String userInfo) {
        User user = gson.fromJson(userInfo, User.class);
        return userService.patchUserInfo(token, user);
    }

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


//    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
//    @GetMapping(value = "/users")
//    public Response<List<User>> findAllUser() {
//        return userService.findAllUser();
//    }
//
//    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
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
//    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
//    @PutMapping(value = "/user")
//    public Response<User> modify(@RequestParam long id, @RequestParam String account, @RequestParam String name) {
//        return userService.modify(id, account, name);
//    }
//
//    @ApiImplicitParam(name = "Task-Authentication", required = true, dataType = "String", paramType = "header")
//    @DeleteMapping(value = "/user")
//    public Response<User> delete(@RequestParam long id) {
//        return userService.delete(id);
//    }
}