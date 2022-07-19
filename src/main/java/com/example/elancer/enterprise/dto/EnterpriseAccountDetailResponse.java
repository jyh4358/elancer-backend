package com.example.elancer.enterprise.dto;

import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.model.enterprise.EnterpriseBizRegistration;
import com.example.elancer.enterprise.model.enterprise.EnterpriseThumbnail;
import com.example.elancer.freelancer.model.FreelancerThumbnail;
import com.example.elancer.member.domain.Address;
import lombok.*;

import java.util.Optional;

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
    private Long sales;
    private String idNumber;
    private String thumbnail;
    private String bizRegistration;

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
                .thumbnail(Optional.ofNullable(enterprise.getEnterpriseThumbnail()).map(EnterpriseThumbnail::getThumbnailPath).orElse(null))
                .bizRegistration(Optional.ofNullable(enterprise.getEnterpriseBizRegistration()).map(EnterpriseBizRegistration::getFilePath).orElse(null))
                .build();
    }
}
