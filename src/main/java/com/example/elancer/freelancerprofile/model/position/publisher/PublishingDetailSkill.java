package com.example.elancer.freelancerprofile.model.position.publisher;

import lombok.Getter;

@Getter
public enum PublishingDetailSkill {
    HTML5("HTML5"),
    CSS("CSS"),
    ACTIONSCRIPT("ActionScript"),
    JQUERY("jQuery"),
    REACT("React.js"),
    VUE_JS("Vue.js"),
    JAVASCRIPT("JavaScript"),
    WORDPRESS("WordPress"),
    BOOTSTRAP("BootStrap"),
    PHOTOSHOP("Photoshop"),
    FLASH("Flash"),
    WEB_ACCESSIBILITY("웹접근성"),
    WEB_STANDARD("웹표준"),
    GIT("Git")
    ;

    private String desc;

    PublishingDetailSkill(String desc) {
        this.desc = desc;
    }
}
