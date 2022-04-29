package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class IntroduceResponse {
    private String introduceName;
    private IntroBackGround introBackGround;
    private String introduceVideoURL;
    private String introduceContent;

    public static IntroduceResponse of(FreelancerProfile freelancerProfile) {
        return new IntroduceResponse(
                freelancerProfile.getIntroduceName(),
                freelancerProfile.getIntroBackGround(),
                freelancerProfile.getIntroduceVideoURL(),
                freelancerProfile.getIntroduceContent()
        );
    }
}
