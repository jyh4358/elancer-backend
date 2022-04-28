package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancerprofile.model.projecthistory.DevelopEnvironment;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectHistoryResponse {
    private String projectTitle;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String clientCompany;
    private String workCompany;
    private DevelopField developField;
    private String developRole;
    private DevelopEnvironment developEnvironment;
    private String responsibilityTask;

    public static ProjectHistoryResponse of(ProjectHistory projectHistory) {
        return new ProjectHistoryResponse(
                projectHistory.getProjectTitle(),
                projectHistory.getProjectStartDate(),
                projectHistory.getProjectEndDate(),
                projectHistory.getClientCompany(),
                projectHistory.getWorkCompany(),
                projectHistory.getDevelopField(),
                projectHistory.getDevelopRole(),
                projectHistory.getDevelopEnvironment(),
                projectHistory.getResponsibilityTask()
        );
    }

    public static List<ProjectHistoryResponse> listOf(List<ProjectHistory> projectHistories) {
        return projectHistories.stream()
                .map(ProjectHistoryResponse::of)
                .collect(Collectors.toList());
    }
}
