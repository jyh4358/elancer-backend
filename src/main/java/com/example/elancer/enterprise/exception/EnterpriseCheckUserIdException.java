package com.example.elancer.enterprise.exception;

public class EnterpriseCheckUserIdException extends RuntimeException{
    private static final String MESSAGE = "이미 가입된 아이디입니다.";

    public EnterpriseCheckUserIdException() {
        super(MESSAGE);
    }
}
