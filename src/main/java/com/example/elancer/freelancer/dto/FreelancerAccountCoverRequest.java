package com.example.elancer.freelancer.dto;

import com.example.elancer.common.validatemessages.FreelancerRequestMessages;
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
    @NotBlank(message = FreelancerRequestMessages.FREELANCER_NAME_BLANK_MESSAGE)
    private String name;
    @NotBlank(message = FreelancerRequestMessages.FREELANCER_PASSWORD_BLANK_MESSAGE)
    private String password;
    @NotBlank(message = FreelancerRequestMessages.FREELANCER_PASSWORD_CHECK_BLANK_MESSAGE)
    private String passwordCheck;
    @NotNull(message = FreelancerRequestMessages.FREELANCER_BIRTHDATE_BLANK_MESSAGE)
    private LocalDate birthDate;
    @NotBlank(message = FreelancerRequestMessages.FREELANCER_EMAIL_BLANK_MESSAGE)
    private String email;
    @NotBlank(message = FreelancerRequestMessages.FREELANCER_PHONE_BLANK_MESSAGE)
    private String phone;
    private String website;
    @NotNull(message = FreelancerRequestMessages.FREELANCER_COUNTRY_TYPE_BLANK_MESSAGE)
    private CountryType countryType;
    @NotBlank(message = FreelancerRequestMessages.FREELANCER_ZIPCODE_BLANK_MESSAGE)
    private String zipcode;
    @NotBlank(message = FreelancerRequestMessages.FREELANCER_MAIN_ADDRESS_BLANK_MESSAGE)
    private String mainAddress;
    @NotBlank(message = FreelancerRequestMessages.FREELANCER_DETAIL_ADDRESS_BLANK_MESSAGE)
    private String detailAddress;

    @NotNull(message = FreelancerRequestMessages.FREELANCER_WORK_TYPE_BLANK_MESSAGE)
    @Size(max = 3)
    private List<FreelancerWorkType> freelancerWorkTypes;
    private String workEtcField;

    private MultipartFile careerForm;

    @NotNull(message = FreelancerRequestMessages.FREELANCER_CAREER_YEAR_BLANK_MESSAGE)
    private int careerYear;
    @NotNull(message = FreelancerRequestMessages.FREELANCER_CAREER_MONTH_BLANK_MESSAGE)
    private int careerMonth;
    @NotNull(message = FreelancerRequestMessages.FREELANCER_HOPE_MONTH_MIN_PAY_BLANK_MESSAGE)
    private int hopeMonthMinPay;
    @NotNull(message = FreelancerRequestMessages.FREELANCER_HOPE_MONTH_MAX_PAY_BLANK_MESSAGE)
    private int hopeMonthMaxPay;

    @NotNull(message = FreelancerRequestMessages.FREELANCER_KOSASTAE_BLANK_MESSAGE)
    private KOSAState kosaState;
    @NotNull(message = FreelancerRequestMessages.FREELANCER_MAIL_RECEPTION_STATE_BLANK_MESSAGE)
    private MailReceptionState mailReceptionState;
    @NotNull(message = FreelancerRequestMessages.FREELANCER_PRESENT_WORK_STATE_BLANK_MESSAGE)
    private PresentWorkState presentWorkState;
    @NotNull(message = FreelancerRequestMessages.FREELANCER_HOPE_WORK_STATE_BLANK_MESSAGE)
    private HopeWorkState hopeWorkState;
    @NotNull(message = FreelancerRequestMessages.FREELANCER_WORK_POSSIBLE_STATE_BLANK_MESSAGE)
    private WorkPossibleState workPossibleState;
    @NotNull(message = FreelancerRequestMessages.FREELANCER_WORK_START_POSSIBLE_DATE_BLANK_MESSAGE)
    private LocalDate workStartPossibleDate;

    private CountryType hopeWorkCountry;
    private String hopeWorkCity;

    public List<WorkType> toWorkTypes() {
        return this.freelancerWorkTypes.stream()
                .map(workType -> WorkType.createWorkType(workType, null))
                .collect(Collectors.toList());
    }
}
