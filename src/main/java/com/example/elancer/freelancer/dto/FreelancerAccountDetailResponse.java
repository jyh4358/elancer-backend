package com.example.elancer.freelancer.dto;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerAccountInfo;
import com.example.elancer.freelancer.model.FreelancerThumbnail;
import com.example.elancer.freelancer.model.FreelancerWorkType;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancer.model.KOSAState;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.PresentWorkState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FreelancerAccountDetailResponse {
    private String name;
    private String thumbnailPath;
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
                Optional.ofNullable(freelancer.getFreelancerThumbnail()).map(FreelancerThumbnail::getThumbnailPath).orElse(null),
                freelancer.getBirthDate(),
                freelancer.getEmail(),
                freelancer.getPhone(),
                freelancer.getWebsite(),
                Optional.ofNullable(freelancer.getAddress()).map(Address::getCountry).orElse(null),
                Optional.ofNullable(freelancer.getAddress()).map(Address::getZipcode).orElse(null),
                Optional.ofNullable(freelancer.getAddress()).map(Address::getMainAddress).orElse(null),
                Optional.ofNullable(freelancer.getAddress()).map(Address::getDetailAddress).orElse(null),
                freelancer.getFreelancerWorkTypesInFreelancer(),
                Optional.ofNullable(freelancer.getFreelancerAccountInfo()).map(FreelancerAccountInfo::getWorkEtcField).orElse(null),
                freelancer.getCareerFormFilePath(),
                Optional.ofNullable(freelancer.getFreelancerAccountInfo()).map(FreelancerAccountInfo::getCareerYear).orElse(null),
                Optional.ofNullable(freelancer.getFreelancerAccountInfo()).map(FreelancerAccountInfo::getCareerMonth).orElse(null),
                Optional.ofNullable(freelancer.getFreelancerAccountInfo()).map(FreelancerAccountInfo::getHopeMonthMinPay).orElse(null),
                Optional.ofNullable(freelancer.getFreelancerAccountInfo()).map(FreelancerAccountInfo::getHopeMonthMaxPay).orElse(null),
                Optional.ofNullable(freelancer.getFreelancerAccountInfo()).map(FreelancerAccountInfo::getKosaState).orElse(null),
                freelancer.getFreelancerAccountInfo().getMailReceptionState(),
                Optional.ofNullable(freelancer.getFreelancerAccountInfo()).map(FreelancerAccountInfo::getPresentWorkState).orElse(null),
                Optional.ofNullable(freelancer.getFreelancerAccountInfo()).map(FreelancerAccountInfo::getHopeWorkState).orElse(null),
                freelancer.getFreelancerAccountInfo().getWorkPossibleState(),
                freelancer.getFreelancerAccountInfo().getWorkStartPossibleDate(),
                Optional.ofNullable(freelancer.getFreelancerAccountInfo()).map(FreelancerAccountInfo::getHopeWorkCountry).orElse(null),
                Optional.ofNullable(freelancer.getFreelancerAccountInfo()).map(FreelancerAccountInfo::getHopeWorkCity).orElse(null)
        );
    }
}
