package com.example.elancer.wishproject.exception;

public class NotExistProjectException extends RuntimeException {
    private static final String MESSAGE = "프로젝트 정보가 없습니다. 잘못된 요청입니다.";

    public NotExistProjectException() {
        super(MESSAGE);
    }
}
