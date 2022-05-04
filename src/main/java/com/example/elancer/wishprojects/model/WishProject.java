package com.example.elancer.wishprojects.model;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.project.model.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishProject extends BasicEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Freelancer freelancer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    public WishProject(Freelancer freelancer, Project project) {
        this.freelancer = freelancer;
        this.project = project;
    }

    public static WishProject createWishProject(Freelancer freelancer, Project project) {
        return new WishProject(freelancer, project);
    }
}
