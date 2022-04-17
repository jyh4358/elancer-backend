package com.example.elancer.freelancer.model;

import com.example.elancer.freelancer.model.position.Position;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.domain.MemberType;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FREELANCER")
public class Freelancer extends Member {
    @NotNull
    private MailReceptionState mailReceptionState;
    @NotNull
    private WorkPossibleState workPossibleState;
    @NotNull
    private LocalDate workStartPossibleDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private FreelancerThumbnail freelancerThumbnail;

    private String introduceName;
    private IntroBackGround introBackGround;
    private String introduceVideoURL;
    private String introduceContent;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Position position;

    public Freelancer(
            String userId,
            String password,
            String name,
            String phone,
            String email,
            MemberType role,
            MailReceptionState mailReceptionState,
            WorkPossibleState workPossibleState,
            LocalDate workStartPossibleDate,
            FreelancerThumbnail freelancerThumbnail
    ) {
        super(userId, password, name, phone, email, role);
        this.mailReceptionState = mailReceptionState;
        this.workPossibleState = workPossibleState;
        this.workStartPossibleDate = workStartPossibleDate;
        this.freelancerThumbnail = freelancerThumbnail;
    }

    public static Freelancer createFreelancer(
            String userId,
            String password,
            String name,
            String phone,
            String email,
            MemberType role,
            MailReceptionState mailReceptionState,
            WorkPossibleState workPossibleState,
            LocalDate workStartPossibleDate,
            FreelancerThumbnail freelancerThumbnail
    ) {
       return new Freelancer(
               userId,
               password,
               name,
               phone,
               email,
               role,
               mailReceptionState,
               workPossibleState,
               workStartPossibleDate,
               freelancerThumbnail
       );
    }

    public void coverIntroduceInFreelancer(String introduceName, IntroBackGround introBackGround, String introduceVideoURL, String introduceContent) {
        this.introduceName = introduceName;
        this.introBackGround = introBackGround;
        this.introduceVideoURL = introduceVideoURL;
        this.introduceContent = introduceContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Freelancer that = (Freelancer) o;
        return mailReceptionState == that.mailReceptionState && workPossibleState == that.workPossibleState && Objects.equals(workStartPossibleDate, that.workStartPossibleDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailReceptionState, workPossibleState, workStartPossibleDate);
    }
}
