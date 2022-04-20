package com.example.elancer.freelancerprofile.model.career;

import com.example.elancer.common.model.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
}
