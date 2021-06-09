package com.zziri.task.exception.custom;

public class UserExistException extends RuntimeException {
    public static final String MESSAGE = "UserExistException";

    public UserExistException() {
        super(MESSAGE);
    }
}
