package com.zziri.todo.exception.custom;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "User Not Found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
