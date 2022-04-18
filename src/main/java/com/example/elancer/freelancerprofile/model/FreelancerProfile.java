package com.example.elancer.freelancerprofile.model;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.model.position.Position;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileNum;

    private String greeting;

    @OneToOne(fetch = FetchType.LAZY)
    private Freelancer freelancer;

    private String introduceName;
    private IntroBackGround introBackGround;
    private String introduceVideoURL;
    private String introduceContent;

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


}
