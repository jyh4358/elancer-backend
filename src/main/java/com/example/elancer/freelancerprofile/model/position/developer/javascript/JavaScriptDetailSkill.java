package com.example.elancer.freelancerprofile.model.position.developer.javascript;

import lombok.Getter;

@Getter
public enum JavaScriptDetailSkill {
    NODE_JS("node.js"),
    ANGULAR_JS("AngularJS"),
    REACT_JS("React.js"),
    VUE_JS("Vue.js"),
    JQUERY("jQuery"),
    JAVASCRIPT("JavaScript"),
    ;

    private String desc;

    JavaScriptDetailSkill(String desc) {
        this.desc = desc;
    }
}
