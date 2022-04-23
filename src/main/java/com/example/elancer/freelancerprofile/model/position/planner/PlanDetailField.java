package com.example.elancer.freelancerprofile.model.position.planner;

import lombok.Getter;

@Getter
public enum PlanDetailField {
    PM("PM"),
    PL("PL"),
    PMO("PMO(사업관리)"),
    SYSTEM_ANALYSIS_AND_PLAN("시스템분석/설계"),
    WEB_PLAN("웹기획"),
    APP_PLAN("앱기획"),
    CONSULTING("컨설팅"),
    OFFER("제안"),
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
    CRYPTOCURRENCY("암호화폐"),
    ;

    private String desc;

    PlanDetailField(String desc) {
        this.desc = desc;
    }
}
