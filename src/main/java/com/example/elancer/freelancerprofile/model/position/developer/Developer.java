package com.example.elancer.freelancerprofile.model.position.developer;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.Position;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DEVEL")
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

    public Developer(FreelancerProfile freelancerProfile, String focusSkill, String role) {
        super(freelancerProfile);
        this.focusSkill = focusSkill;
        this.role = role;
    }

    public void coverDeveloper(
            String focusSkill,
            String role,
            List<JavaSkill> javaSkills,
            List<MobileAppSkill> mobileAppSkills,
            List<PhpOrAspSkill> phpOrAspSkills,
            List<DotNetSkill> dotNetSkills,
            List<JavaScriptSkill> javaScriptSkills,
            List<CSkill> cOrCPlusplusSkills,
            List<DBSkill> dbSkills,
            String etcSkill
    ) {
        this.focusSkill = focusSkill;
        this.role = role;
        coverJavaSkills(javaSkills);
        coverMobileSkills(mobileAppSkills);
        coverPhpOrAspSkills(phpOrAspSkills);
        coverDBSkills(dotNetSkills);
        coverJavaScriptSkills(javaScriptSkills);
        coverCSkills(cOrCPlusplusSkills);
        coverDotNetSkills(dbSkills);
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

        }
        this.phpOrAspSkills = phpOrAspSkills;
    }

    private void coverDotNetSkills(List<DBSkill> dbSkills) {
        this.dotNetSkills.clear();
        this.dbSkills = dbSkills;
    }

    private void coverCSkills(List<CSkill> cOrCPlusplusSkills) {
        this.cSkills.clear();
        this.cSkills = cOrCPlusplusSkills;
    }

    private void coverJavaScriptSkills(List<JavaScriptSkill> javaScriptSkills) {
        this.javaScriptSkills.clear();
        this.javaScriptSkills = javaScriptSkills;
    }

    private void coverDBSkills(List<DotNetSkill> dotNetSkills) {
        this.dbSkills.clear();
        this.dotNetSkills = dotNetSkills;
    }

    public Developer(
            FreelancerProfile freelancerProfile,
            String focusSkill,
            String role,
            List<JavaSkill> javaSkills,
            List<MobileAppSkill> mobileAppSkills,
            List<PhpOrAspSkill> phpOrAspSkills,
            List<DotNetSkill> dotNetSkills,
            List<JavaScriptSkill> javaScriptSkills,
            List<CSkill> cOrCPlusplusSkills,
            List<DBSkill> dbSkills,
            String etcSkill
    ) {
        super(freelancerProfile);
        this.focusSkill = focusSkill;
        this.role = role;
        this.javaSkills = javaSkills;
        this.mobileAppSkills = mobileAppSkills;
        this.phpOrAspSkills = phpOrAspSkills;
        this.dotNetSkills = dotNetSkills;
        this.javaScriptSkills = javaScriptSkills;
        this.cSkills = cOrCPlusplusSkills;
        this.dbSkills = dbSkills;
        this.etcSkill = etcSkill;
    }

    public static Developer createDeveloper(
            FreelancerProfile freelancerProfile,
            String focusSkill,
            String role,
            List<JavaSkill> javaSkills,
            List<MobileAppSkill> mobileAppSkills,
            List<PhpOrAspSkill> phpOrAspSkills,
            List<DotNetSkill> dotNetSkills,
            List<JavaScriptSkill> javaScriptSkills,
            List<CSkill> cOrCPlusplusSkills,
            List<DBSkill> dbSkills,
            String etcSkill
    ) {
        return new Developer(
                freelancerProfile,
                focusSkill,
                role,
                javaSkills,
                mobileAppSkills,
                phpOrAspSkills,
                dotNetSkills,
                javaScriptSkills,
                cOrCPlusplusSkills,
                dbSkills,
                etcSkill
        );
    }
}
