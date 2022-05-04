package com.example.elancer.freelancerprofile.model.position.developer.dbskill;

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
public class DBSkill extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private DBDetailSkill dbDetailSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;

    public DBSkill(DBDetailSkill dbDetailSkill, Developer developer) {
        this.dbDetailSkill = dbDetailSkill;
        this.developer = developer;
    }

    public static DBSkill createDBSkill(DBDetailSkill dbDetailSkill, Developer developer) {
        return new DBSkill(dbDetailSkill, developer);
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }
}
