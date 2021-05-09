package com.zziri.todo.exception.custom;

public class AccountSigninFailedException extends RuntimeException {
    private static final String MESSAGE = "Signin Failed";

    public AccountSigninFailedException() {
        super(MESSAGE);
    }
}
