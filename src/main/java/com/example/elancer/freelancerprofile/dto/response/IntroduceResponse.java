package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancer.model.IntroBackGround;
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

    public static IntroduceResponse of(String introduceName, IntroBackGround introBackGround, String introduceVideoURL, String introduceContent) {
        return new IntroduceResponse(introduceName, introBackGround, introduceVideoURL, introduceContent);
    }
}
