package com.zziri.task.controller;

import com.zziri.task.exception.custom.AuthenticationEntryPointException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {
    @GetMapping(value = "/exception/entrypoint")
    public void entrypointException() {
        throw new AuthenticationEntryPointException();
    }
}
