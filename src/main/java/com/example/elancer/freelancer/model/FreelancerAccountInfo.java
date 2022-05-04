package com.example.elancer.freelancer.model;

import com.example.elancer.member.domain.CountryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerAccountInfo {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkType> workTypes = new ArrayList<>();
    private String workEtcField;

    private int careerYear;
    private int careerMonth;
    private int hopeMonthMinPay;
    private int hopeMonthMaxPay;

    @Enumerated(EnumType.STRING)
    private KOSAState kosaState;

    @Enumerated(EnumType.STRING)
    private MailReceptionState mailReceptionState;

    @Enumerated(EnumType.STRING)
    private PresentWorkState presentWorkState;

    @Enumerated(EnumType.STRING)
    private HopeWorkState hopeWorkState;

    @Enumerated(EnumType.STRING)
    private WorkPossibleState workPossibleState;

    private LocalDate workStartPossibleDate;

    @Enumerated(EnumType.STRING)
    private CountryType hopeWorkCountry;
    private String hopeWorkCity;

    public FreelancerAccountInfo(MailReceptionState mailReceptionState, WorkPossibleState workPossibleState, LocalDate workStartPossibleDate) {
        this.mailReceptionState = mailReceptionState;
        this.workPossibleState = workPossibleState;
        this.workStartPossibleDate = workStartPossibleDate;
    }

    public static FreelancerAccountInfo basicOf(MailReceptionState mailReceptionState, WorkPossibleState workPossibleState, LocalDate workStartPossibleDate) {
        return new FreelancerAccountInfo(mailReceptionState, workPossibleState, workStartPossibleDate);
    }

    public void coverFreelancerAccountInfo(
            String workEtcField,
            int careerYear,
            int careerMonth,
            int hopeMonthMinPay,
            int hopeMonthMaxPay,
            KOSAState kosaState,
            MailReceptionState mailReceptionState,
            PresentWorkState presentWorkState,
            HopeWorkState hopeWorkState,
            WorkPossibleState workPossibleState,
            LocalDate workStartPossibleDate,
            CountryType hopeWorkCountry,
            String hopeWorkCity
    ) {
        this.workEtcField = workEtcField;
        this.careerYear = careerYear;
        this.careerMonth = careerMonth;
        this.hopeMonthMinPay = hopeMonthMinPay;
        this.hopeMonthMaxPay = hopeMonthMaxPay;
        this.kosaState = kosaState;
        this.mailReceptionState = mailReceptionState;
        this.presentWorkState = presentWorkState;
        this.hopeWorkState = hopeWorkState;
        this.workPossibleState = workPossibleState;
        this.workStartPossibleDate = workStartPossibleDate;
        this.hopeWorkCountry = hopeWorkCountry;
        this.hopeWorkCity = hopeWorkCity;
    }

    public void coverWorkTypes(List<WorkType> workTypes, Freelancer freelancer) {
        this.workTypes.clear();
        for (WorkType workType : workTypes) {
            workType.setFreelancer(freelancer);
        }
        this.workTypes.addAll(workTypes);
    }
}
