package com.zziri.todo.exception.custom;

public class UserExistException extends RuntimeException {
    public static final String MESSAGE = "User already exist";

    public UserExistException() {
        super(MESSAGE);
    }
}
