package com.example.elancer.freelancer.model;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private FreelancerProfile freelancerProfile;

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
