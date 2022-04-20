package com.example.elancer.freelancerprofile.model.career;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Career extends BasicEntity {

    private String companyName;

    private String departmentName;

    @Enumerated(EnumType.STRING)
    private CompanyPosition companyPosition;

    private LocalDate careerStartDate;
    private LocalDate careerEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private FreelancerProfile freelancerProfile;

    public Career(String companyName, String departmentName, CompanyPosition companyPosition, LocalDate careerStartDate, LocalDate careerEndDate) {
        this.companyName = companyName;
        this.departmentName = departmentName;
        this.companyPosition = companyPosition;
        this.careerStartDate = careerStartDate;
        this.careerEndDate = careerEndDate;
    }

    public static Career createCareer(String companyName, String departmentName, CompanyPosition companyPosition, LocalDate careerStartDate, LocalDate careerEndDate) {
        return new Career(companyName, departmentName, companyPosition, careerStartDate, careerEndDate);
    }

    public void setFreelancerProfile(FreelancerProfile freelancerProfile) {
        this.freelancerProfile = freelancerProfile;
    }
}
