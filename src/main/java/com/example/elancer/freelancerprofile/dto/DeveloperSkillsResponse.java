package com.example.elancer.freelancerprofile.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeveloperSkillsResponse {
    private List<String> javaDetailSkillNames;
    private List<String> mobileDetailSkillNames;
    private List<String> phpOrAspDetailSkillNames;
    private List<String> dotNetDetailSkillNames;
    private List<String> javaScriptDetailSkillNames;
    private List<String> cDetailSkillNames;
    private List<String> dbDetailSkillNames;

    public DeveloperSkillsResponse(
            List<String> javaDetailSkillNames,
            List<String> mobileDetailSkillNames,
            List<String> phpOrAspDetailSkillNames,
            List<String> dotNetDetailSkillNames,
            List<String> javaScriptDetailSkillNames,
            List<String> cDetailSkillNames,
            List<String> dbDetailSkillNames
    ) {
        this.javaDetailSkillNames = javaDetailSkillNames;
        this.mobileDetailSkillNames = mobileDetailSkillNames;
        this.phpOrAspDetailSkillNames = phpOrAspDetailSkillNames;
        this.dotNetDetailSkillNames = dotNetDetailSkillNames;
        this.javaScriptDetailSkillNames = javaScriptDetailSkillNames;
        this.cDetailSkillNames = cDetailSkillNames;
        this.dbDetailSkillNames = dbDetailSkillNames;
    }
}
