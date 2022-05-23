package com.example.elancer.member.dto;

import com.example.elancer.member.domain.MemberType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponse {

    private MemberType memberType;

    private String accessToken;
    private String refreshToken;
}
