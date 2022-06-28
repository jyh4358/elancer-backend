package com.example.elancer.waitproject.model;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.project.model.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaitProject extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private WaitStatus waitStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Freelancer freelancer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;



    public WaitProject(Freelancer freelancer, Project project) {
        this.waitStatus = WaitStatus.WAITING;
        this.freelancer = freelancer;
        this.project = project;
    }

    public static WaitProject createWaitProject(Freelancer freelancer, Project project) {
        return new WaitProject(freelancer, project);
    }

    public void changeWaitStatus() {
        this.waitStatus = WaitStatus.WORKING;
    }
}
