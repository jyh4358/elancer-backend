package com.example.elancer.freelancerprofile.dto.request;

import com.example.elancer.freelancerprofile.model.projecthistory.DevelopEnvironment;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProjectHistoryRequest {
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

    public ProjectHistoryRequest(
            String projectTitle,
            LocalDate projectStartDate,
            LocalDate projectEndDate,
            String clientCompany,
            String workCompany,
            DevelopField developField,
            String developRole,
            String developEnvironmentModel,
            String developEnvironmentOS,
            String developEnvironmentLanguage,
            String developEnvironmentDBName,
            String developEnvironmentTool,
            String developEnvironmentCommunication,
            String developEnvironmentEtc,
            String responsibilityTask
    ) {
        this.projectTitle = projectTitle;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.clientCompany = clientCompany;
        this.workCompany = workCompany;
        this.developField = developField;
        this.developRole = developRole;
        this.developEnvironmentModel = developEnvironmentModel;
        this.developEnvironmentOS = developEnvironmentOS;
        this.developEnvironmentLanguage = developEnvironmentLanguage;
        this.developEnvironmentDBName = developEnvironmentDBName;
        this.developEnvironmentTool = developEnvironmentTool;
        this.developEnvironmentCommunication = developEnvironmentCommunication;
        this.developEnvironmentEtc = developEnvironmentEtc;
        this.responsibilityTask = responsibilityTask;
    }

    public static ProjectHistory toProjectHistory(ProjectHistoryRequest projectHistoryRequest) {
        return ProjectHistory.createProjectHistory(
                projectHistoryRequest.getProjectTitle(),
                projectHistoryRequest.getProjectStartDate(),
                projectHistoryRequest.getProjectEndDate(),
                projectHistoryRequest.getClientCompany(),
                projectHistoryRequest.getWorkCompany(),
                projectHistoryRequest.getDevelopField(),
                projectHistoryRequest.getDevelopRole(),
                DevelopEnvironment.of(
                        projectHistoryRequest.getDevelopEnvironmentModel(),
                        projectHistoryRequest.getDevelopEnvironmentOS(),
                        projectHistoryRequest.getDevelopEnvironmentLanguage(),
                        projectHistoryRequest.getDevelopEnvironmentDBName(),
                        projectHistoryRequest.getDevelopEnvironmentTool(),
                        projectHistoryRequest.getDevelopEnvironmentCommunication(),
                        projectHistoryRequest.getDevelopEnvironmentEtc()
                ),
                projectHistoryRequest.getResponsibilityTask()
        );
    }
}
