package com.example.elancer.project.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfinityListResponse {

    private List<ProjectBoxResponse> projectBoxResponses = new ArrayList<>();
    private boolean hasNext;

    public InfinityListResponse(List<ProjectBoxResponse> projectBoxResponses, boolean hasNext) {
        this.projectBoxResponses = projectBoxResponses;
        this.hasNext = hasNext;
    }

    public static InfinityListResponse of(List<ProjectBoxResponse> projectBoxResponses, boolean hasNext) {
        return new InfinityListResponse(
                projectBoxResponses,
                hasNext
        );
    }
}
