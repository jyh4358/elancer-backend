package com.example.elancer.freelancerprofile.dto.request;

import com.example.elancer.freelancer.model.IntroBackGround;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class IntroduceCoverRequest {
    private String introName;
    private IntroBackGround introBackGround;
    private String introVideoUrl;
    private String introContent;
}
