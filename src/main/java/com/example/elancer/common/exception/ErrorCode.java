package com.example.elancer.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 에러코드 400이 맞을려나..
    CHECK_PASSWORD(400, "MEMBER-ERR-400", "비밀번호가 일치하지 않습니다.");

    private int status;
    private String errorCode;
    private String message;

}
