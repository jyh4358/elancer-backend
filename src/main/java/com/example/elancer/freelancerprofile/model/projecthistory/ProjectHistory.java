package com.example.elancer.freelancerprofile.model.projecthistory;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectHistory extends BasicEntity {

    private String projectTitle;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String clientCompany;
    private String workCompany;

    @Enumerated(EnumType.STRING)
    private DevelopField developField;
    private String developRole;

    @Embedded
    private DevelopEnvironment developEnvironment;

    private String responsibilityTask;

    @ManyToOne(fetch = FetchType.LAZY)
    private FreelancerProfile freelancerProfile;

    public ProjectHistory(
            String projectTitle,
            LocalDate projectStartDate,
            LocalDate projectEndDate,
            String clientCompany,
            String workCompany,
            DevelopField developField,
            String developRole,
            DevelopEnvironment developEnvironment,
            String responsibilityTask
    ) {
        this.projectTitle = projectTitle;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.clientCompany = clientCompany;
        this.workCompany = workCompany;
        this.developField = developField;
        this.developRole = developRole;
        this.developEnvironment = developEnvironment;
        this.responsibilityTask = responsibilityTask;
    }

    public static ProjectHistory createProjectHistory(
            String projectTitle,
            LocalDate projectStartDate,
            LocalDate projectEndDate,
            String clientCompany,
            String workCompany,
            DevelopField developField,
            String developRole,
            DevelopEnvironment developEnvironment,
            String responsibilityTask
    ) {
        return new ProjectHistory(
                projectTitle,
                projectStartDate,
                projectEndDate,
                clientCompany,
                workCompany,
                developField,
                developRole,
                developEnvironment,
                responsibilityTask
        );
    }

    public void setFreelancerProfile(FreelancerProfile freelancerProfile) {
        this.freelancerProfile = freelancerProfile;
    }
}
