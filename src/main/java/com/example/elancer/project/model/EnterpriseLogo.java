package com.example.elancer.project.model;

import com.example.elancer.common.model.BasicEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterpriseLogo extends BasicEntity {

    @NotNull
    private String logoPath;

    @OneToOne(fetch = FetchType.LAZY)
    private Project project;

    public EnterpriseLogo(String logoPath, Project project) {
        this.logoPath = logoPath;
        this.project = project;
    }

    public static EnterpriseLogo createEnterpriseLogo(String logoPath, Project project) {
        return new EnterpriseLogo(logoPath, project);
    }
}
