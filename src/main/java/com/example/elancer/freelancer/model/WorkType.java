package com.example.elancer.freelancer.model;

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
public class WorkType extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private FreelancerWorkType freelancerWorkType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Freelancer freelancer;

    public WorkType(FreelancerWorkType freelancerWorkType, Freelancer freelancer) {
        this.freelancerWorkType = freelancerWorkType;
        this.freelancer = freelancer;
    }

    public static WorkType createWorkType(FreelancerWorkType freelancerWorkType, Freelancer freelancer) {
        return new WorkType(freelancerWorkType, freelancer);
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }
}
