package com.zziri.task.exception.custom;

public class UserNotFoundException extends RuntimeException {
    public static final String MESSAGE = "UserNotFoundException";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
