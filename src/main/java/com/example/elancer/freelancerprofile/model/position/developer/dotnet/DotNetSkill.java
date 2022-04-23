package com.example.elancer.freelancerprofile.model.position.developer.dotnet;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.position.Developer;
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
public class DotNetSkill extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private DotNetDetailSkill dotNetDetailSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;
}
