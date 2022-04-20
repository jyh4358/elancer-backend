package com.example.elancer.freelancerprofile.model;

import com.example.elancer.common.exception.WrongRequestException;
import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.career.Career;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerProfile extends BasicEntity {

    private String greeting;

    @OneToOne(fetch = FetchType.LAZY)
    private Freelancer freelancer;

    private String introduceName;

    @Enumerated(EnumType.STRING)
    private IntroBackGround introBackGround;

    private String introduceVideoURL;
    private String introduceContent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freelancerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcademicAbility> academicAbilities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freelancerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Career> careers = new ArrayList<>();


//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Position position;

    public FreelancerProfile(String greeting, Freelancer freelancer) {
        this.greeting = greeting;
        this.freelancer = freelancer;
    }

    public void coverIntroduceInFreelancer(String introduceName, IntroBackGround introBackGround, String introduceVideoURL, String introduceContent) {
        this.introduceName = introduceName;
        this.introBackGround = introBackGround;
        this.introduceVideoURL = introduceVideoURL;
        this.introduceContent = introduceContent;
    }

    public void checkFreelancerAndProfileMatcher(String userId) {
        if (!this.freelancer.getUserId().equals(userId)) {
            throw new WrongRequestException("프로필에 대한 요청자와 프리랜서가 동일하지 않습니다. 잘못된 요청입니다.");
        }
    }

    public void coverAcademicAbilities(List<AcademicAbility> academicAbilities) {
        this.academicAbilities.clear();
        for (AcademicAbility academicAbility : academicAbilities) {
            academicAbility.setFreelancerProfile(this);
        }
        this.academicAbilities.addAll(academicAbilities);
    }

    public void coverCareers(List<Career> careers) {
        this.careers.clear();
        for (Career career : careers) {
            career.setFreelancerProfile(this);
        }
        this.careers.addAll(careers);
    }


}
