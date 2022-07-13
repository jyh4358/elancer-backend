package com.example.elancer.project.dto;

import com.example.elancer.freelancer.model.Freelancer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleFreelancerDto {
    private String thumbnailUrl;
    private String username;

    public SimpleFreelancerDto(String thumbnailUrl, String username) {
        this.thumbnailUrl = thumbnailUrl;
        this.username = username;
    }

    public static SimpleFreelancerDto of(Freelancer freelancer) {
        return new SimpleFreelancerDto(
                "",
                freelancer.getName()
        );
    }
}
