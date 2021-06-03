package com.zziri.todo.exception.custom;

public class AuthenticationEntryPointException extends RuntimeException {
    public static final String MESSAGE = "AuthenticationEntryPointException";

    public AuthenticationEntryPointException() {
        super(MESSAGE);
    }
}
