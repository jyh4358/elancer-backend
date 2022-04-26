package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CSkill;
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

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    //dto에서 변환해주는 작업은 필드가 null로 요청될수 있기에 당장은 옳은 방식이라고 생각함.
    public List<JavaSkill> toJavaSkill(Developer developer) {
        if (this.javaDetailSkills == null) {
            return new ArrayList<>();
        }

        return this.javaDetailSkills.stream()
                .map(javaDetailSkill -> JavaSkill.createJavaSkill(javaDetailSkill, developer))
                .collect(Collectors.toList());
    }

    public List<MobileAppSkill> toMobileAppSkill(Developer developer) {
        if (this.mobileAppDetailSkills == null) {
            return new ArrayList<>();
        }

        return this.mobileAppDetailSkills.stream()
                .map(mobileAppDetailSkill -> MobileAppSkill.createMobileAppSkill(mobileAppDetailSkill, developer))
                .collect(Collectors.toList());
    }

    public List<PhpOrAspSkill> toPhpOrAspSkill(Developer developer) {
        if (this.phpOrAspDetailSkills == null) {
            return new ArrayList<>();
        }
        return this.phpOrAspDetailSkills.stream()
                .map(phpOrAspDetailSkill -> PhpOrAspSkill.createPhpOrAspSkill(phpOrAspDetailSkill, developer))
                .collect(Collectors.toList());
    }

    public List<DotNetSkill> toDotNetSkill(Developer developer) {
        if (this.dotNetDetailSkills == null) {
            return new ArrayList<>();
        }

        return this.dotNetDetailSkills.stream()
                .map(dotNetDetailSkill -> DotNetSkill.createDotNetSkill(dotNetDetailSkill, developer))
                .collect(Collectors.toList());
    }

    public List<JavaScriptSkill> toJavaScriptSkill(Developer developer) {
        if (this.javaScriptDetailSkills == null) {
            return new ArrayList<>();
        }
        return this.javaScriptDetailSkills.stream()
                .map(javaScriptDetailSkill -> JavaScriptSkill.createJavaScriptSkill(javaScriptDetailSkill, developer))
                .collect(Collectors.toList());
    }

    public List<CSkill> toCSkill(Developer developer) {
        if (this.cDetailSkills == null) {
            return new ArrayList<>();
        }
        return this.cDetailSkills.stream()
                .map(cDetailSkill -> CSkill.createCSkill(cDetailSkill, developer))
                .collect(Collectors.toList());
    }

    public List<DBSkill> toDBSkill(Developer developer) {
        if (this.dbDetailSkills == null) {
            return new ArrayList<>();
        }
        return this.dbDetailSkills.stream()
                .map(dbDetailSkill -> DBSkill.createDBSkill(dbDetailSkill, developer))
                .collect(Collectors.toList());
    }

    // min @Getter가 cDetailSkills는 만들어내지 못하는것 같다. 에러는 요청조차 안들어가고 테스트가 싪패하는 걸로 봐서는 object -> json 과정에서 문제가 생기는거 같은데 밑의 get 추가해주니 에러 사라짐.
    public List<CDetailSkill> getcDetailSkills() {
        return cDetailSkills;
    }
}
