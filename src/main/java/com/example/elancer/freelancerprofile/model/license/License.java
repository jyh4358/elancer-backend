package com.example.elancer.freelancerprofile.model.license;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class License extends BasicEntity {
    private String licenseTitle;
    private String licenseIssuer;
    private LocalDate acquisitionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private FreelancerProfile freelancerProfile;

    public License(String licenseTitle, String licenseIssuer, LocalDate acquisitionDate) {
        this.licenseTitle = licenseTitle;
        this.licenseIssuer = licenseIssuer;
        this.acquisitionDate = acquisitionDate;
    }

    public static License createLicense(String licenseTitle, String licenseIssuer, LocalDate acquisitionDate) {
        return new License(licenseTitle, licenseIssuer, acquisitionDate);
    }
}
