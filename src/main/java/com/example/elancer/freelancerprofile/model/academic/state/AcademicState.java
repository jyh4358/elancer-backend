package com.example.elancer.freelancerprofile.model.academic.state;

import lombok.Getter;

@Getter
public enum AcademicState {
    IN_SCHOOL("재학"),
    LEAVE_OF_ABSENCE("휴학"),
    DROP_OUT("중퇴"),
    GRADUATION("졸업"),
    COMPLETION("수료/이수")
    ;

    private String desc;

    AcademicState(String desc) {
        this.desc = desc;
    }
}
