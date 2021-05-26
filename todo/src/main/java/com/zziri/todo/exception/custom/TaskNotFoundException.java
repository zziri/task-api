package com.zziri.todo.exception.custom;

public class TaskNotFoundException extends RuntimeException {
    public static final String MESSAGE = "TaskNotFound";

    public TaskNotFoundException() {
        super(MESSAGE);
    }
}
