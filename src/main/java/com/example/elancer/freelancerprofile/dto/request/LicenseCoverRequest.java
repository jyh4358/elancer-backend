package com.example.elancer.freelancerprofile.dto.request;

import com.example.elancer.freelancerprofile.model.license.License;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LicenseCoverRequest {
    private String licenseTitle;
    private String licenseIssuer;
    private LocalDate acquisitionDate;

    public static License toLicense(LicenseCoverRequest licenseCoverRequest) {
        return License.createLicense(
                licenseCoverRequest.getLicenseTitle(),
                licenseCoverRequest.getLicenseIssuer(),
                licenseCoverRequest.getAcquisitionDate()
        );
    }
}
