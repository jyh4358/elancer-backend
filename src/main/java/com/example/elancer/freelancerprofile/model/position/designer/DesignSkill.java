package com.example.elancer.freelancerprofile.model.position.designer;

import com.example.elancer.common.model.BasicEntity;
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
public class DesignSkill extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private DesignDetailSkill designDetailSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Designer designer;

    public DesignSkill(DesignDetailSkill designDetailSkill, Designer designer) {
        this.designDetailSkill = designDetailSkill;
        this.designer = designer;
    }

    public static DesignSkill createDesignSkill(DesignDetailSkill designDetailSkill, Designer designer) {
        return new DesignSkill(designDetailSkill, designer);
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }
}
