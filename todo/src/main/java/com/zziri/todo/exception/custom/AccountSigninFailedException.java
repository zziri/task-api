package com.zziri.todo.exception.custom;

public class AccountSigninFailedException extends RuntimeException {
    public static final String MESSAGE = "Signin Failed";

    public AccountSigninFailedException() {
        super(MESSAGE);
    }
}
