package com.example.elancer.freelancerprofile.model.position.developer.mobileskill;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
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
public class MobileAppSkill extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private MobileAppDetailSkill mobileAppDetailSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;

    public MobileAppSkill(MobileAppDetailSkill mobileAppDetailSkill, Developer developer) {
        this.mobileAppDetailSkill = mobileAppDetailSkill;
        this.developer = developer;
    }

    public static MobileAppSkill createMobileAppSkill(MobileAppDetailSkill mobileAppDetailSkill, Developer developer) {
        return new MobileAppSkill(mobileAppDetailSkill, developer);
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }
}
