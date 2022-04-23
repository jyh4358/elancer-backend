package com.example.elancer.freelancerprofile.model.position.developer.dotnet;

public enum DotNetDetailSkill {
    ASP_DOT_NET("ASP.net"),
    C("C"),
    C_PLUS_PLUS("C++"),
    C_SHOP("C#"),
    MFC("MFC"),
    OPENGL("OpenGL"),
    DEVEXPRESS("DevExpress"),
    VBA("VBA"),
    ;

    private String desc;

    DotNetDetailSkill(String desc) {
        this.desc = desc;
    }
}
