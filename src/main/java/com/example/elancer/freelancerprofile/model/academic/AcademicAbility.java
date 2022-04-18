package com.example.elancer.freelancerprofile.model.academic;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
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
public class AcademicAbility extends BasicEntity {

    private String schoolName;
    private SchoolLevel schoolLevel;
    private LocalDate enterSchoolDate;
    private LocalDate graduationDate;
    private AcademicState academicState;
    private String majorName;

    @ManyToOne(fetch = FetchType.LAZY)
    private FreelancerProfile freelancerProfile;

    public AcademicAbility(
            String schoolName,
            SchoolLevel schoolLevel,
            LocalDate enterSchoolDate,
            LocalDate graduationDate,
            AcademicState academicState,
            String majorName
    ) {
        this.schoolName = schoolName;
        this.schoolLevel = schoolLevel;
        this.enterSchoolDate = enterSchoolDate;
        this.graduationDate = graduationDate;
        this.academicState = academicState;
        this.majorName = majorName;
    }

    public static AcademicAbility createAcademicAbility(
            String schoolName,
            SchoolLevel schoolLevel,
            LocalDate enterSchoolDate,
            LocalDate graduationDate,
            AcademicState academicState,
            String majorName
    ) {
        return new AcademicAbility(
                schoolName,
                schoolLevel,
                enterSchoolDate,
                graduationDate,
                academicState,
                majorName
        );
    }

    public void setFreelancerProfile(FreelancerProfile freelancerProfile) {
        this.freelancerProfile = freelancerProfile;
    }
}
