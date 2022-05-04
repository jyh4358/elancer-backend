package com.example.elancer.login.auth.exception;

public class UserPasswordException extends RuntimeException{

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public UserPasswordException() {
        super(MESSAGE);
    }
}
