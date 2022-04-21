package com.example.elancer.freelancerprofile.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EducationAndLicenseAndLanguageRequests {
    private List<EducationCoverRequest> educationCoverRequests;
    private List<LicenseCoverRequest> licenseCoverRequests;
    private List<LanguageCoverRequest> languageCoverRequests;

}
