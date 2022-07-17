package com.example.elancer.freelancerprofile.model.position.developer.javascript;

import lombok.Getter;

@Getter
public enum JavaScriptDetailSkill {
    NODEJS("node.js"),
    ANGULAR("AngularJS"),
    REACT("React.js"),
    VUE("Vue.js"),
    JQUERY("jQuery"),
    JAVASCRIPT("JavaScript")
    ;

    private String desc;

    JavaScriptDetailSkill(String desc) {
        this.desc = desc;
    }
}
