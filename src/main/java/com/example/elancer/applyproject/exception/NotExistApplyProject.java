package com.example.elancer.applyproject.exception;

public class NotExistApplyProject extends RuntimeException{
    private static final String MESSAGE = "해당 프로젝트에 지원한 프리랜서가 존재하지 않습니다.";

    public NotExistApplyProject() {
        super(MESSAGE);
    }
}
