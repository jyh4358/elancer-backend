package com.example.elancer.freelancerprofile.dto.request;

import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AcademicAbilityCoverRequest {
    private String schoolName;
    private SchoolLevel schoolLevel;
    private LocalDate enterSchoolDate;
    private LocalDate graduationDate;
    private AcademicState academicState;
    private String majorName;

    // min 영;;; 구현 자체가 좀 이상한거 같다. 일단은 서비스로직 가독성을 위해 이렇게 구현했다.
    public static AcademicAbility toAcademicAbility(AcademicAbilityCoverRequest academicAbilityCoverRequest) {
        return AcademicAbility.createAcademicAbility(
                academicAbilityCoverRequest.getSchoolName(),
                academicAbilityCoverRequest.getSchoolLevel(),
                academicAbilityCoverRequest.getEnterSchoolDate(),
                academicAbilityCoverRequest.getGraduationDate(),
                academicAbilityCoverRequest.getAcademicState(),
                academicAbilityCoverRequest.getMajorName()
        );
    }
}
