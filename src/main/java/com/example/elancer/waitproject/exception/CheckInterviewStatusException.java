package com.example.elancer.waitproject.exception;

public class CheckInterviewStatusException extends RuntimeException {
    private static final String MESSAGE = "인터뷰 수락하지 않은 프리랜서입니다. 다시 확인해주세요";

    public CheckInterviewStatusException() {
        super(MESSAGE);
    }
}
