package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancerprofile.model.education.Education;
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
public class EducationResponse {
    private String educationTitle;
    private String educationOrganization;
    private LocalDate educationStartDate;
    private LocalDate educationEndDate;

    public static EducationResponse of(Education education) {
        return new EducationResponse(
                education.getEducationTitle(),
                education.getEducationOrganization(),
                education.getEducationStartDate(),
                education.getEducationEndDate()
        );
    }

    public static List<EducationResponse> listOf(List<Education> educations) {
        return educations.stream()
                .map(EducationResponse::of)
                .collect(Collectors.toList());
    }
}
