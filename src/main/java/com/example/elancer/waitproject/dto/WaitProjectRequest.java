package com.example.elancer.waitproject.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WaitProjectRequest {
    @NotNull
    private Long projectNum;

    @NotNull
    private Long freelancerNum;
}
