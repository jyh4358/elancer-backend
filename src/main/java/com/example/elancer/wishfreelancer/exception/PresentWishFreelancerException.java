package com.example.elancer.wishfreelancer.exception;

public class PresentWishFreelancerException extends RuntimeException{
    private static final String MESSAGE = "이미 인재 스크랩에 등록되어있습니다";

    public PresentWishFreelancerException() {
        super(MESSAGE);
    }
}
