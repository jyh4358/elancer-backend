package com.example.elancer.freelancerprofile.model.position.developer.javaskill;

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
public class JavaSkill extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private JavaDetailSkill javaDetailSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;

    public JavaSkill(JavaDetailSkill javaDetailSkill, Developer developer) {
        this.javaDetailSkill = javaDetailSkill;
        this.developer = developer;
    }

    public static JavaSkill createJavaSkill(JavaDetailSkill javaDetailSkill, Developer developer) {
        return new JavaSkill(javaDetailSkill, developer);
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }
}
