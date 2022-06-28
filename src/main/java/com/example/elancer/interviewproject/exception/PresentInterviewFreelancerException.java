package com.example.elancer.interviewproject.exception;

public class PresentInterviewFreelancerException extends RuntimeException {
    private static final String MESSAGE = "인터뷰 요청한 프리랜서입니다. 다시 확인해주세요";

    public PresentInterviewFreelancerException() {
        super(MESSAGE);
    }
}
