package com.example.elancer.freelancerprofile.dto;

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
}
