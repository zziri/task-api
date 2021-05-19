package com.zziri.todo.exception.custom;

public class CommunicationException extends RuntimeException {
    public static final String MESSAGE = "Communication Fail";

    public CommunicationException() {
        super(MESSAGE);
    }
}
