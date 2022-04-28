package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancerprofile.model.license.License;
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
public class LicenseResponse {
    private String licenseTitle;
    private String licenseIssuer;
    private LocalDate acquisitionDate;

    public static LicenseResponse of(License license) {
        return new LicenseResponse(license.getLicenseTitle(), license.getLicenseIssuer(), license.getAcquisitionDate());
    }

    public static List<LicenseResponse> listOf(List<License> licenses) {
        return licenses.stream()
                .map(LicenseResponse::of)
                .collect(Collectors.toList());
    }
}
