package com.example.elancer.interviewproject.model;

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
public class InterviewProject extends BasicEntity {

    private InterviewSatus interviewSatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Freelancer freelancer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    public InterviewProject(Freelancer freelancer, Project project) {
        this.interviewSatus = InterviewSatus.WAITING;
        this.freelancer = freelancer;
        this.project = project;
    }

    public static InterviewProject createApplyProject(Freelancer freelancer, Project project) {
        return new InterviewProject(freelancer, project);
    }

    public void changeInterviewStatus(InterviewSatus interviewSatus) {
        this.interviewSatus = interviewSatus;
    }
}
