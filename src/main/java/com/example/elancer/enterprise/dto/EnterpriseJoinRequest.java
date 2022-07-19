package com.example.elancer.enterprise.dto;

import com.example.elancer.member.domain.Address;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.exception.CheckPasswordException;
import com.example.elancer.member.domain.MemberType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EnterpriseJoinRequest {

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

    private Long sales;
    @NotBlank
    private String idNumber;

    private String thumbnail;

    private String bizRegistrationFile;

    // todo - 이후에 사업자 등록증 파일 구현


    public Enterprise toEntity() {
        return Enterprise.builder()
                .userId(userId)
                .password(password1)
                .name(name)
                .phone(phone)
                .email(email)
                .website(website)
                .address(new Address(address.getCountry(), address.getZipcode(), address.getMainAddress(), address.getDetailAddress()))
                .role(MemberType.ENTERPRISE)
                .companyName(companyName)
                .companyPeople(companyPeople)
                .position(position)
                .telNumber(telNumber)
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
