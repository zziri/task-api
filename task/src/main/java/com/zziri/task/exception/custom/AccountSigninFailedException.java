package com.zziri.task.exception.custom;

public class AccountSigninFailedException extends RuntimeException {
    public static final String MESSAGE = "AccountSigninFailedException";

    public AccountSigninFailedException() {
        super(MESSAGE);
    }
}
