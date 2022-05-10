package com.example.elancer.enterprise.dto;

import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.member.domain.Address;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnterpriseAccountDetailResponse {

    private String companyName;
    private Integer companyPeople;
    private String name;
    private String position;
    private String phone;
    private String telNumber;
    private String email;
    private String website;
    private Address address;
    private String bizContents;
    private Integer sales;
    private String idNumber;

    public static EnterpriseAccountDetailResponse of(Enterprise enterprise) {
        return EnterpriseAccountDetailResponse.builder()
                .companyName(enterprise.getCompanyName())
                .companyPeople(enterprise.getCompanyPeople())
                .name(enterprise.getName())
                .position(enterprise.getPosition())
                .phone(enterprise.getPhone())
                .telNumber(enterprise.getTelNumber())
                .email(enterprise.getEmail())
                .website(enterprise.getWebsite())
                .address(enterprise.getAddress())
                .bizContents(enterprise.getBizContents())
                .sales(enterprise.getSales())
                .idNumber(enterprise.getIdNumber())
                .build();
    }
}
