package com.example.elancer.enterprise.exception;

public class NotExistEnterpriseException extends RuntimeException{
    private static final String MESSAGE = "회원이 존재하지 않습니다.";

    public NotExistEnterpriseException() {
        super(MESSAGE);
    }
}
