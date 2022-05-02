package com.example.elancer.freelancer.dto;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerWorkType;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancer.model.KOSAState;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.PresentWorkState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.member.domain.CountryType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FreelancerAccountDetailResponse {
    private String name;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String website;
    private CountryType countryType;
    private String zipcode;
    private String mainAddress;
    private String detailAddress;
    private List<FreelancerWorkType> freelancerWorkTypes;
    private String workEtcField;
    private String fileName;
    private int careerYear;
    private int careerMonth;
    private int hopeMonthMinPay;
    private int hopeMonthMaxPay;
    private KOSAState kosaState;
    private MailReceptionState mailReceptionState;
    private PresentWorkState presentWorkState;
    private HopeWorkState hopeWorkState;
    private WorkPossibleState workPossibleState;
    private LocalDate workStartPossibleDate;
    private CountryType hopeWorkCountry;
    private String hopeWorkCity;

    public static FreelancerAccountDetailResponse of(Freelancer freelancer) {
        return new FreelancerAccountDetailResponse(
                freelancer.getName(),
                freelancer.getBirthDate(),
                freelancer.getEmail(),
                freelancer.getPhone(),
                freelancer.getWebsite(),
                freelancer.getAddress().getCountry(),
                freelancer.getAddress().getZipcode(),
                freelancer.getAddress().getMainAddress(),
                freelancer.getAddress().getDetailAddress(),
                freelancer.getFreelancerWorkTypesInFreelancer(),
                freelancer.getFreelancerAccountInfo().getWorkEtcField(),
                freelancer.getCareerFormFileName(),
                freelancer.getFreelancerAccountInfo().getCareerYear(),
                freelancer.getFreelancerAccountInfo().getCareerMonth(),
                freelancer.getFreelancerAccountInfo().getHopeMonthMinPay(),
                freelancer.getFreelancerAccountInfo().getHopeMonthMaxPay(),
                freelancer.getFreelancerAccountInfo().getKosaState(),
                freelancer.getFreelancerAccountInfo().getMailReceptionState(),
                freelancer.getFreelancerAccountInfo().getPresentWorkState(),
                freelancer.getFreelancerAccountInfo().getHopeWorkState(),
                freelancer.getFreelancerAccountInfo().getWorkPossibleState(),
                freelancer.getFreelancerAccountInfo().getWorkStartPossibleDate(),
                freelancer.getFreelancerAccountInfo().getHopeWorkCountry(),
                freelancer.getFreelancerAccountInfo().getHopeWorkCity()
        );
    }
}
