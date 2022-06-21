package com.example.elancer.interviewproject.model;

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
public class InterviewProject extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private InterviewStatus interviewStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Freelancer freelancer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    public InterviewProject(InterviewStatus interviewStatus, Freelancer freelancer, Project project) {
        this.interviewStatus = interviewStatus;
        this.freelancer = freelancer;
        this.project = project;
    }

    public static InterviewProject createInterviewProject(Freelancer freelancer, Project project) {
        return new InterviewProject(InterviewStatus.WAITING, freelancer, project);
    }

    public void changeInterviewStatus(InterviewStatus interviewStatus) {
        this.interviewStatus = interviewStatus;
    }
}
