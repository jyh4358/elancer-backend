package com.example.elancer.token.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Code {

    UNKNOWN_ERROR(400, "토큰이 존재하지 않습니다."),
    WRONG_TYPE_TOKEN(400, "변조된 토큰입니다."),
    EXPIRED_TOKEN(402, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(400, "변조된 토큰입니다.");

    private int code;
    private String message;
}
