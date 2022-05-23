package com.example.elancer.member.domain;

import lombok.Getter;

@Getter
public enum MemberType {
    AMDIN("ROLE_ADMIN", "관리자"),
    FREELANCER("ROLE_FREELANCER", "프리랜서"),
    ENTERPRISE("ROLE_ENTERPRISE", "엔터프라이즈");

    private String key;
    private String type;

    MemberType(String key, String type) {
        this.key = key;
        this.type = type;
    }
}
