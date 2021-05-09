package com.zziri.todo.exception.custom;

public class AuthenticationEntryPointException extends RuntimeException {
    public static final String MESSAGE = "Permission Denied";

    public AuthenticationEntryPointException() {
        super(MESSAGE);
    }
}
