package com.example.elancer.project.exception;

public class NotExistProjectException extends RuntimeException{
    private static final String MESSAGE = "해당 프로젝트가 존재하지 않습니다.";

    public NotExistProjectException() {
        super(MESSAGE);
    }
}
