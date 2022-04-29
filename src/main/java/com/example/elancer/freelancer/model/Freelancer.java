package com.example.elancer.freelancer.model;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.domain.MemberType;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FREELANCER")
public class Freelancer extends Member {

    private LocalDate birthDate;

    @Embedded
    private FreelancerAccountInfo freelancerAccountInfo;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private FreelancerThumbnail freelancerThumbnail;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private CareerForm careerForm;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private FreelancerProfile freelancerProfile;

    public Freelancer(
            String userId,
            String password,
            String name,
            String phone,
            String email,
            String website,
            Address address,
            MemberType role,
            MailReceptionState mailReceptionState,
            WorkPossibleState workPossibleState,
            LocalDate workStartPossibleDate,
            FreelancerThumbnail freelancerThumbnail
    ) {
        super(userId, password, name, phone, email, website, address, role);
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
            String website,
            Address address,
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
               website,
               address,
               role,
               mailReceptionState,
               workPossibleState,
               workStartPossibleDate,
               freelancerThumbnail
       );
    }

    public void updateFreelancer(
            String name,
            String password,
            String email,
            String phone,
            String website,
            Address address,
            LocalDate birthDate,
            int careerYear,
            int careerMonth,
            int hopeMonthMinPay,
            int hopeMonthMaxPay
    ) {
        updateMember(name, password, email, phone, website, address);
        this.birthDate = birthDate;


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
