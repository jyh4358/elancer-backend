package com.example.elancer.freelancerprofile.model.career;

public enum CompanyPosition {
    CHAIRMAN("회장"),
    A_VICE_CHAIRMAN("부회장"),
    PRESIDENT("사장"),
    A_VICE_PRESIDENT("부사장"),
    EXECUTIVE_DIRECTOR("전무"),
    DIRECTOR("상무"),
    A_VICE_DIRECTOR("이사"),
    GENERAL_MANAGER("부장"),
    DEPUTY_GENERAL_MANAGER("차장"),
    SUPERVISOR("과장"),
    HEAD_OF_DEPARTMENT("실장"),
    TEAM_LEADER("팀장"),
    ASSISTANT_MANAGER("대리"),
    SECTION_CHIEF("계장"),
    TEAM_MANAGER("주임"),
    STAFF("사원"),
    LABORATORY_CHIEF("연구소장"),
    TOP_RESEARCHER("수석 연구원"),
    SENIOR_RESEARCHER("책임 연구원"),
    A_VICE_SENIOR_RESEARCHER("선임 연구원"),
    CHIEF_RESEARCHER("주임 연구원"),
    RESEARCHER("연구원"),
    TEMPORARY_POSITION("임시직"),
    ETC("기타"),
    MANAGER("매니저")
    ;

    private String desc;

    CompanyPosition(String desc) {
        this.desc = desc;
    }
}
