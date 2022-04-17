package com.example.elancer.freelancer.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerThumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thumbnailNum;

    @NotNull
    private String thumbnailPath;

    @OneToOne(fetch = FetchType.LAZY)
    private Freelancer freelancer;

    public FreelancerThumbnail(String thumbnailPath, Freelancer freelancer) {
        this.thumbnailPath = thumbnailPath;
        this.freelancer = freelancer;
    }

    public static FreelancerThumbnail createFreelancerThumbnail(String thumbnailPath, Freelancer freelancer) {
        return new FreelancerThumbnail(thumbnailPath, freelancer);
    }

}
