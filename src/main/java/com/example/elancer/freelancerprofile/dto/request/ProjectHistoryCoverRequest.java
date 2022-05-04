package com.example.elancer.freelancerprofile.dto.request;

import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectHistoryCoverRequest {
    private String projectTitle;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String clientCompany;
    private String workCompany;
    private DevelopField developField;
    private String developRole;
    private String developEnvironmentModel;
    private String developEnvironmentOS;
    private String developEnvironmentLanguage;
    private String developEnvironmentDBName;
    private String developEnvironmentTool;
    private String developEnvironmentCommunication;
    private String developEnvironmentEtc;
    private String responsibilityTask;
}
