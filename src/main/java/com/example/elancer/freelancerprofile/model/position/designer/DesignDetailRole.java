package com.example.elancer.freelancerprofile.model.position.designer;

import lombok.Getter;

@Getter
public enum DesignDetailRole {
    WEBDESIGN("웹디자인"),
    APPDESIGN("앱디자인"),
    PUBLISHANDEDITDESIGN("출판/편집 디자인"),
    GAMEDESIGN("게임 디자인"),
    FREEBIEDESIGN("판촉물 디자인"),
    THREEDDESIGN("3D 디자인"),
    GRAPHICDESIGN("그래픽 디자인"),
    PACKAGEDESIGN("패키지 디자인"),
    ARTDIRECTION("아트 디렉션"),
    ANIMATION("애니메이션"),
    LOGOBRANDING("로고 브랜딩")
    ;

    private String desc;

    DesignDetailRole(String desc) {
        this.desc = desc;
    }
}
