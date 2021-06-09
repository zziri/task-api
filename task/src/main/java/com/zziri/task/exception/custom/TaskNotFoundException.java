package com.zziri.task.exception.custom;

public class TaskNotFoundException extends RuntimeException {
    public static final String MESSAGE = "TaskNotFoundException";

    public TaskNotFoundException() {
        super(MESSAGE);
    }
}
