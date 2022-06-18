package com.example.elancer.interviewproject.exception;

public class NotExistInterviewException extends RuntimeException{
    private static final String MESSAGE = "요청된 인터뷰가 없습니다. 잘못된 요청입니다.";

    public NotExistInterviewException() {
        super(MESSAGE);
    }
}
