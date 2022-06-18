package com.example.elancer.waitproject.exeption;

public class NotExistWaitProject extends RuntimeException {
    private static final String MESSAGE = "투입된 프로젝트가 없습니다. 잘못된 요청입니다.";

    public NotExistWaitProject() {
        super(MESSAGE);
    }
}
