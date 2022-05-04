package com.example.elancer.freelancer.model;

import lombok.Getter;

@Getter
public enum FreelancerWorkType {
    SHOPPING_MALL("쇼핑몰"),
    TRAVEL_AGENCY("여행사"),
    FINANCE("금융"),
    STOCK("증권"),
    CARD("카드"),
    INSURANCE("보험"),
    UNIVERSITY("대학"),
    HOSPITAL("병원"),
    PUBLIC_OFFICE("공공기관"),
    DISTRIBUTION("물류"),
    ACCOUNTING("회계"),
    PRODUCTION("제조"),
    CONSTRUCTION("건설"),
    COMMUNICATION("통신"),
    CIRCULATION("유통"),
    PRODUCE("생산"),
    MEDIA("미디어"),
    EDUCATION("교육"),
    SEMICONDUCTOR("반도체"),
    VEHICLE("자동차"),
    CRYPTOCURRENCY("암호화폐"),
    BLOCKCHAIN("블록체인"),
    BIGDATA("빅데이터"),
    ETC("기타")
    ;

    private String desc;

    FreelancerWorkType(String desc) {
        this.desc = desc;
    }
}
