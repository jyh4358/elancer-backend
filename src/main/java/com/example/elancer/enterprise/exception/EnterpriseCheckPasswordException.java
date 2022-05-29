package com.example.elancer.enterprise.exception;

public class EnterpriseCheckPasswordException extends RuntimeException {
    private static final String MESSAGE = "비밀번호와 비밀번호 확인이 서로 다릅니다. 요청을 확인해주세요..";

    public EnterpriseCheckPasswordException() {
        super(MESSAGE);
    }
}
