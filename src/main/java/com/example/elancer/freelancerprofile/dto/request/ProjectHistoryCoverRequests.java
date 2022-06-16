package com.example.elancer.freelancerprofile.dto.request;

import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectHistoryCoverRequests {
    private List<ProjectHistoryRequest> projectHistoryRequestList;

    public ProjectHistoryCoverRequests(List<ProjectHistoryRequest> projectHistoryRequestList) {
        this.projectHistoryRequestList = projectHistoryRequestList;
    }
}
