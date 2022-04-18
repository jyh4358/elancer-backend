package com.example.elancer.enterprise.dto;

import com.example.elancer.enterprise.domain.enterprise.Address;
import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.exception.CheckPasswordException;
import com.example.elancer.member.domain.MemberType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class EnterpriseJoinRequest {

    private String userId;
    private String password1;
    private String password2;
    private String name;
    private String phone;
    private String email;


    private String companyName;
    private int companyPeople;
    private String position;
    private String telNumber;
    private String website;
    private Address address;
    private String bizContents;
    private int sales;
    private String idNumber;


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
