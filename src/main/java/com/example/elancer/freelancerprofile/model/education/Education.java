package com.example.elancer.freelancerprofile.model.education;

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
public class Education extends BasicEntity {

    private String educationTitle;
    private String educationOrganization;
    private LocalDate educationStartDate;
    private LocalDate educationEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private FreelancerProfile freelancerProfile;

    public Education(String educationTitle, String educationOrganization, LocalDate educationStartDate, LocalDate educationEndDate) {
        this.educationTitle = educationTitle;
        this.educationOrganization = educationOrganization;
        this.educationStartDate = educationStartDate;
        this.educationEndDate = educationEndDate;
    }

    public static Education createEducation(String educationTitle, String educationOrganization, LocalDate educationStartDate, LocalDate educationEndDate) {
        return new Education(educationTitle, educationOrganization, educationStartDate, educationEndDate);
    }

    public void setFreelancerProfile(FreelancerProfile freelancerProfile) {
        this.freelancerProfile = freelancerProfile;
    }
}
