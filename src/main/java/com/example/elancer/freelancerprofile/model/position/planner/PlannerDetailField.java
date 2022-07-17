package com.example.elancer.freelancerprofile.model.position.planner;

import lombok.Getter;

@Getter
public enum PlannerDetailField {
    PM("PM"),
    PL("PL"),
    PMO("PMO(사업관리)"),
    SYSTEMANALYSISANDPLAN("시스템분석/설계"),
    WEBPLAN("웹기획"),
    APPPLAN("앱기획"),
    CONSULTING("컨설팅"),
    OFFER("제안"),
    SHOPPINGMALL("쇼핑몰"),
    TRAVELAGENCY("여행사"),
    FINANCE("금융"),
    STOCK("증권"),
    CARD("카드"),
    INSURANCE("보험"),
    UNIVERSITY("대학"),
    HOSPITAL("병원"),
    PUBLICOFFICE("공공기관"),
    DISTRIBUTION("물류"),
    ACCOUNTING("회계"),
    PRODUCTION("제조"),
    CONSTRUCTION("건설"),
    CRYPTOCURRENCY("암호화폐")
    ;

    private String desc;

    PlannerDetailField(String desc) {
        this.desc = desc;
    }
}
