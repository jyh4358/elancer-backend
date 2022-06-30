package com.example.elancer.freelancerprofile.model.position.developer;

import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspSkill;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@DiscriminatorValue("DEVELOPER")
public class Developer extends Position {

    @NotNull
    private String focusSkill;

    @NotNull
    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JavaSkill> javaSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MobileAppSkill> mobileAppSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhpOrAspSkill> phpOrAspSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DotNetSkill> dotNetSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JavaScriptSkill> javaScriptSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CSkill> cSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DBSkill> dbSkills = new ArrayList<>();

    private String etcSkill;

    public Developer(PositionType positionType, FreelancerProfile freelancerProfile, String focusSkill, String role) {
        super(positionType, freelancerProfile);
        this.focusSkill = focusSkill;
        this.role = role;
    }

    //develop 생성시 밑의 스킬들을 등록하려면 develop 데이터가 필요해 먼저 develop 생성후 스킬들을 할당해준다.
    public static Developer createBasicDeveloper(PositionType positionType, FreelancerProfile freelancerProfile, String focusSkill, String role) {
        return new Developer(positionType, freelancerProfile, focusSkill, role);
    }

    @Override
    public List<String> getAllSkillNames() {
        List<String> allSkillNames = new ArrayList<>();
        allSkillNames.addAll(javaSkills.stream()
                .map(javaSkill -> javaSkill.getJavaDetailSkill().getDesc())
                .collect(Collectors.toList()));
        allSkillNames.addAll(mobileAppSkills.stream()
                .map(mobileAppSkill -> mobileAppSkill.getMobileAppDetailSkill().getDesc())
                .collect(Collectors.toList()));
        allSkillNames.addAll(phpOrAspSkills.stream()
                .map(phpOrAspSkill -> phpOrAspSkill.getPhpOrAspDetailSkill().getDesc())
                .collect(Collectors.toList()));
        allSkillNames.addAll(dotNetSkills.stream()
                .map(dotNetSkill -> dotNetSkill.getDotNetDetailSkill().getDesc())
                .collect(Collectors.toList()));
        allSkillNames.addAll(javaScriptSkills.stream()
                .map(javaScriptSkill -> javaScriptSkill.getJavaScriptDetailSkill().getDesc())
                .collect(Collectors.toList()));
        allSkillNames.addAll(cSkills.stream()
                .map(cSkill -> cSkill.getCDetailSkill().getDesc())
                .collect(Collectors.toList()));
        allSkillNames.addAll(dbSkills.stream()
                .map(dbSkill -> dbSkill.getDbDetailSkill().getDesc())
                .collect(Collectors.toList()));
        allSkillNames.add(etcSkill);
        return allSkillNames;
    }

    public void coverDeveloperSkills(
            List<JavaSkill> javaSkills,
            List<MobileAppSkill> mobileAppSkills,
            List<PhpOrAspSkill> phpOrAspSkills,
            List<DotNetSkill> dotNetSkills,
            List<JavaScriptSkill> javaScriptSkills,
            List<CSkill> cOrCPlusplusSkills,
            List<DBSkill> dbSkills,
            String etcSkill
    ) {
        coverJavaSkills(javaSkills);
        coverMobileSkills(mobileAppSkills);
        coverPhpOrAspSkills(phpOrAspSkills);
        coverDotNetSkills(dotNetSkills);
        coverJavaScriptSkills(javaScriptSkills);
        coverCSkills(cOrCPlusplusSkills);
        coverDBSkills(dbSkills);
        this.etcSkill = etcSkill;
    }

    private void coverJavaSkills(List<JavaSkill> javaSkills) {
        this.javaSkills.clear();
        for (JavaSkill javaSkill : javaSkills) {
            javaSkill.setDeveloper(this);
        }
        this.javaSkills.addAll(javaSkills);
    }

    private void coverMobileSkills(List<MobileAppSkill> mobileAppSkills) {
        this.mobileAppSkills.clear();
        for (MobileAppSkill mobileAppSkill : mobileAppSkills) {
            mobileAppSkill.setDeveloper(this);
        }
        this.mobileAppSkills.addAll(mobileAppSkills);
    }

    private void coverPhpOrAspSkills(List<PhpOrAspSkill> phpOrAspSkills) {
        this.phpOrAspSkills.clear();
        for (PhpOrAspSkill phpOrAspSkill : phpOrAspSkills) {
            phpOrAspSkill.setDeveloper(this);
        }
        this.phpOrAspSkills = phpOrAspSkills;
    }

    private void coverDotNetSkills(List<DotNetSkill> dbSkills) {
        this.dotNetSkills.clear();
        for (DotNetSkill dotNetSkill : dotNetSkills) {
            dotNetSkill.setDeveloper(this);
        }
        this.dotNetSkills = dbSkills;
    }

    private void coverCSkills(List<CSkill> cSkills) {
        this.cSkills.clear();
        for (CSkill cSkill : cSkills) {
            cSkill.setDeveloper(this);
        }
        this.cSkills = cSkills;
    }

    private void coverJavaScriptSkills(List<JavaScriptSkill> javaScriptSkills) {
        this.javaScriptSkills.clear();
        for (JavaScriptSkill javaScriptSkill : javaScriptSkills) {
            javaScriptSkill.setDeveloper(this);
        }
        this.javaScriptSkills = javaScriptSkills;
    }

    private void coverDBSkills(List<DBSkill> dbSkills) {
        this.dbSkills.clear();
        for (DBSkill dbSkill : dbSkills) {
            dbSkill.setDeveloper(this);
        }
        this.dbSkills = dbSkills;
    }
}
