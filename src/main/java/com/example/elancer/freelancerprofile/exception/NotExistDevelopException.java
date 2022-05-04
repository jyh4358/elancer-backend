package com.example.elancer.freelancerprofile.exception;

public class NotExistDevelopException extends RuntimeException {
    private static final String MESSAGE = "해당 프리랜서는 개발자 포지션이 아닙니다. 다시 확인해 주세요.";

    public NotExistDevelopException() {
        super(MESSAGE);
    }
}
