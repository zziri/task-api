package com.zziri.task.exception.custom;

public class CommunicationException extends RuntimeException {
    public static final String MESSAGE = "CommunicationException";

    public CommunicationException() {
        super(MESSAGE);
    }
}
