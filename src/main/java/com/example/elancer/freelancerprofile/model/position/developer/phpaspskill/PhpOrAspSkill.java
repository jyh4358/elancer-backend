package com.example.elancer.freelancerprofile.model.position.developer.phpaspskill;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppSkill;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhpOrAspSkill extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private PhpOrAspDetailSkill phpOrAspDetailSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;

    public PhpOrAspSkill(PhpOrAspDetailSkill phpOrAspDetailSkill, Developer developer) {
        this.phpOrAspDetailSkill = phpOrAspDetailSkill;
        this.developer = developer;
    }

    public static PhpOrAspSkill createPhpOrAspSkill(PhpOrAspDetailSkill phpOrAspDetailSkill, Developer developer) {
        return new PhpOrAspSkill(phpOrAspDetailSkill, developer);
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }
}
