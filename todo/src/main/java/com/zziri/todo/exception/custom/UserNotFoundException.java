package com.zziri.todo.exception.custom;

public class UserNotFoundException extends RuntimeException {
    public static final String MESSAGE = "UserNotFoundException";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
