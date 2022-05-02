package com.example.elancer.project.model;

import lombok.Getter;

@Getter
public enum ProjectBackGround {
    //TODO 당장은 임의로만 구현했어요. 나머지는 윤호님 스타일대로 구현해주세요!
    BLACK("검정배경")
    ;

    private String desc;

    ProjectBackGround(String desc) {
        this.desc = desc;
    }
}
