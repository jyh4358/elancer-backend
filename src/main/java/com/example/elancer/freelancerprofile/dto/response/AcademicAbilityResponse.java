package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AcademicAbilityResponse {
    private String schoolName;
    private SchoolLevel schoolLevel;
    private String schoolLevelDescription;
    private LocalDate enterSchoolDate;
    private LocalDate graduationDate;
    private AcademicState academicState;
    private String majorName;

    public static AcademicAbilityResponse of(AcademicAbility academicAbility) {
        return new AcademicAbilityResponse(
                academicAbility.getSchoolName(),
                academicAbility.getSchoolLevel(),
                academicAbility.getSchoolLevel().getDesc(),
                academicAbility.getEnterSchoolDate(),
                academicAbility.getGraduationDate(),
                academicAbility.getAcademicState(),
                academicAbility.getMajorName()
        );
    }

    public static List<AcademicAbilityResponse> listOf(List<AcademicAbility> academicAbilityList) {
        return academicAbilityList.stream()
                .map(AcademicAbilityResponse::of)
                .collect(Collectors.toList());
    }
}
