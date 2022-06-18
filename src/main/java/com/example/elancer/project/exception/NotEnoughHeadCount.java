package com.example.elancer.project.exception;

public class NotEnoughHeadCount extends RuntimeException {
    private static final String MESSAGE = "투입 인원이 부족합니다.";

    public NotEnoughHeadCount() {
        super(MESSAGE);
    }
}
