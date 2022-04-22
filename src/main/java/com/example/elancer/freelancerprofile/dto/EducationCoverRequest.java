package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancerprofile.model.education.Education;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EducationCoverRequest {
    private String educationTitle;
    private String educationOrganization;
    private LocalDate educationStartDate;
    private LocalDate educationEndDate;

    public static Education toEducation(EducationCoverRequest educationCoverRequest) {
        return Education.createEducation(
                educationCoverRequest.getEducationTitle(),
                educationCoverRequest.getEducationOrganization(),
                educationCoverRequest.getEducationStartDate(),
                educationCoverRequest.getEducationEndDate()
        );
    }
}
