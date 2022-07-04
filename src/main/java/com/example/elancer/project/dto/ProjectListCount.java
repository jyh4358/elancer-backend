package com.example.elancer.project.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectListCount {
    private int applyProjectCount;
    private int interviewProjectCount;
    private int waitProjectCount;
    private int processingProjectCount;
    private int completionProjectCount;

    public ProjectListCount(int applyProjectCount, int interviewProjectCount, int waitProjectCount, int processingProjectCount, int completionProjectCount) {
        this.applyProjectCount = applyProjectCount;
        this.interviewProjectCount = interviewProjectCount;
        this.waitProjectCount = waitProjectCount;
        this.processingProjectCount = processingProjectCount;
        this.completionProjectCount = completionProjectCount;
    }

    public static ProjectListCount of(int applyProjectCount, int interviewProjectCount, int waitProjectCount, int processingProjectCount, int completionProjectCount) {
        return new ProjectListCount(
                applyProjectCount,
                interviewProjectCount,
                waitProjectCount,
                processingProjectCount,
                completionProjectCount
        );
    }
}
