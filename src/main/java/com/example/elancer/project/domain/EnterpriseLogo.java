package com.example.elancer.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnterpriseLogo {
    SAMSUNG("삼성"),
    LG("LG"),
    KT("KT"),
    SK("SK"),
    COUPANG("쿠팡"),
    WOOWAHAN("우아한형제들"),
    LOTTE("롯데"),
    NONGHYUP("농협"),
    SHINHAN("신한"),
    IBK("기업"),
    KBSTAR("국민");


    private String desc;

}
