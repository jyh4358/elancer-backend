package com.example.elancer.enterprise.dto;

import com.example.elancer.enterprise.model.enterprise.Enterprise;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnterpriseSimpleDetailResponse {
    private String companyName;
    private String name;
    private String position;
    private String telNumber;
    private String phone;
    private String email;

    public static EnterpriseSimpleDetailResponse of(Enterprise enterprise) {
        return EnterpriseSimpleDetailResponse.builder()
                .companyName(enterprise.getCompanyName())
                .name(enterprise.getName())
                .position(enterprise.getPosition())
                .telNumber(enterprise.getTelNumber())
                .phone(enterprise.getPhone())
                .email(enterprise.getEmail())
                .build();
    }
}
