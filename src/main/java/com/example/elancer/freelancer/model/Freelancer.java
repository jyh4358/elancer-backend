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

//    private IntroBackGround introBackGround;
//    private String introduceVideoURL;
//    private String introduceContent;
//
//
//    @OneToOne(fetch = FetchType.LAZY,mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Position position;

    public Freelancer(
            String userId,
            String password,
            String name,
            String phone,
            String email,
            MemberType role,
            MailReceptionState mailReceptionState,
            WorkPossibleState workPossibleState,
            LocalDate workStartPossibleDate
    ) {
        super(userId, password, name, phone, email, role);
        this.mailReceptionState = mailReceptionState;
        this.workPossibleState = workPossibleState;
        this.workStartPossibleDate = workStartPossibleDate;
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
            LocalDate workStartPossibleDate
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
               workStartPossibleDate
       );
    }


}
