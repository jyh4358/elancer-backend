package com.example.elancer.member.dto;

import com.example.elancer.member.domain.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberContactResponse {
    private String name;
    private String phone;
    private String email;

    public static MemberContactResponse of(Member member) {
        return new MemberContactResponse(
                member.getName(),
                member.getPhone(),
                member.getEmail()
        );
    }
}
