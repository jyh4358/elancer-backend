package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.ClangSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspSkill;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeveloperResponse {
    private List<String> focusSkills;
    private List<String> roles;
    private List<JavaDetailSkill> javaDetailSkills;
    private List<MobileAppDetailSkill> mobileAppDetailSkills;
    private List<PhpOrAspDetailSkill> phpOrAspDetailSkills;
    private List<DotNetDetailSkill> dotNetDetailSkills;
    private List<JavaScriptDetailSkill> javaScriptDetailSkills;
    private List<CDetailSkill> cDetailSkills;
    private List<DBDetailSkill> dbDetailSkills;
    private String etcSkill;

    public static DeveloperResponse of(Developer developer) {
        return new DeveloperResponse(
                StringEditor.editStringToStringList(developer.getFocusSkill()),
                StringEditor.editStringToStringList(developer.getRole()),
                developer.getJavaSkills().stream()
                        .map(JavaSkill::getJavaDetailSkill)
                        .collect(Collectors.toList()),
                developer.getMobileAppSkills().stream()
                        .map(MobileAppSkill::getMobileAppDetailSkill)
                        .collect(Collectors.toList()),
                developer.getPhpOrAspSkills().stream()
                        .map(PhpOrAspSkill::getPhpOrAspDetailSkill)
                        .collect(Collectors.toList()),
                developer.getDotNetSkills().stream()
                        .map(DotNetSkill::getDotNetDetailSkill)
                        .collect(Collectors.toList()),
                developer.getJavaScriptSkills().stream()
                        .map(JavaScriptSkill::getJavaScriptDetailSkill)
                        .collect(Collectors.toList()),
                developer.getCSkills().stream()
                        .map(ClangSkill::getCDetailSkill)
                        .collect(Collectors.toList()),
                developer.getDbSkills().stream()
                        .map(DBSkill::getDbDetailSkill)
                        .collect(Collectors.toList()),
                developer.getEtcSkill()
        );
    }
}
