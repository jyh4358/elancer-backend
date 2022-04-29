package com.example.elancer.freelancer.model;

import com.example.elancer.member.domain.CountryType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FreelancerAccountInfo {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkType> workType = new ArrayList<>();
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

//    public static FreelancerAccountInfo of(
//
//    ) {
//
//
//    }
}
