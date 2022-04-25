package com.example.elancer.freelancerprofile.model.position.developer.mobileskill;

public enum MobileAppDetailSkill {
    HYBRID("Hybrid"),
    ANDROID("Android"),
    IOS_OBJECT_C("IOS(Object-C)"),
    IOS_SWIFT("IOS(Swift)"),
    WEBVIEW("WebView"),
    IOT("IoT")
    ;

    private String desc;

    MobileAppDetailSkill(String desc) {
        this.desc = desc;
    }
}
