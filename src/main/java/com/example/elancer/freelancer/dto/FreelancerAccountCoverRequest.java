package com.example.elancer.freelancer.dto;

import com.example.elancer.freelancer.model.FreelancerWorkType;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancer.model.KOSAState;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.PresentWorkState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.model.WorkType;
import com.example.elancer.member.domain.CountryType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FreelancerAccountCoverRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordCheck;
    @NotNull
    private LocalDate birthDate;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    private String website;
    @NotNull
    private CountryType countryType;
    @NotBlank
    private String zipcode;
    @NotBlank
    private String mainAddress;
    @NotBlank
    private String detailAddress;

    @NotNull
    @Size(max = 3)
    private List<FreelancerWorkType> freelancerWorkTypes;
    private String workEtcField;

    private MultipartFile careerForm;

    @NotNull
    private int careerYear;
    @NotNull
    private int careerMonth;
    @NotNull
    private int hopeMonthMinPay;
    @NotNull
    private int hopeMonthMaxPay;

    @NotNull
    private KOSAState kosaState;
    @NotNull
    private MailReceptionState mailReceptionState;
    @NotNull
    private PresentWorkState presentWorkState;
    @NotNull
    private HopeWorkState hopeWorkState;
    @NotNull
    private WorkPossibleState workPossibleState;
    @NotNull
    private LocalDate workStartPossibleDate;

    private CountryType hopeWorkCountry;
    private String hopeWorkCity;

    public List<WorkType> toWorkTypes() {
        return this.freelancerWorkTypes.stream()
                .map(workType -> WorkType.createWorkType(workType, null))
                .collect(Collectors.toList());
    }
}
