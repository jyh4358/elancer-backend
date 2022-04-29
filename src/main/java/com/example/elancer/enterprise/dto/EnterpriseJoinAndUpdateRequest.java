package com.example.elancer.enterprise.dto;

import com.example.elancer.member.domain.Address;
import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.exception.CheckPasswordException;
import com.example.elancer.member.domain.MemberType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
public class EnterpriseJoinAndUpdateRequest {

    @NotBlank
    private String userId;
    @NotBlank
    private String password1;
    @NotBlank
    private String password2;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @Email
    @NotBlank
    private String email;


    @NotBlank
    private String companyName;

    private Integer companyPeople;
    @NotBlank
    private String position;
    @NotBlank
    private String telNumber;
    private String website;

    private Address address;

    private String bizContents;

    private Integer sales;
    @NotBlank
    private String idNumber;

    // todo - 이후에 사업자 등록증 파일 구현


    public Enterprise toEntity() {
        return Enterprise.builder()
                .userId(userId)
                .password(password1)
                .name(name)
                .phone(phone)
                .email(email)
                .role(MemberType.ENTERPRISE)
                .companyName(companyName)
                .companyPeople(companyPeople)
                .position(position)
                .telNumber(telNumber)
                .website(website)
                .address(address)
                .bizContents(bizContents)
                .sales(sales)
                .idNumber(idNumber)
                .build();
    }

    public void checkPwd() {
        if (!this.password1.equals(password2)) {
            throw new CheckPasswordException();
       }
    }

}
