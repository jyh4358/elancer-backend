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
}
