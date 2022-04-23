package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancerprofile.model.position.developer.cskill.CDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspDetailSkill;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeveloperCoverRequest {
    @NotBlank
    private String focusSkill;
    @NotBlank
    private String role;
    private List<JavaDetailSkill> javaDetailSkills;
    private List<MobileAppDetailSkill> mobileAppDetailSkills;
    private List<PhpOrAspDetailSkill> phpOrAspDetailSkills;
    private List<DotNetDetailSkill> dotNetDetailSkills;
    private List<JavaScriptDetailSkill> javaScriptDetailSkills;
    private List<CDetailSkill> cDetailSkills;
    private List<DBDetailSkill> dbDetailSkills;
    private String etcSkill;
}
