package com.example.elancer.enterprise.dto;


import com.example.elancer.member.domain.Address;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EnterpriseUpdateRequest {

    @NotBlank
    private String companyName;

    // Integer는 NotBlank, NotEmpty 사용하면 에러발생
    @NotNull
    private Integer companyPeople;

    @NotBlank
    private String name;

    private String position;

    private String password1;

    private String password2;

    @NotBlank
    private String phone;

    @NotBlank
    private String telNumber;

    @NotBlank
    @Email
    private String email;

    private String website;

    private Address address;

    private String bizContents;

    private Long sales;

    private String idNumber;

    // todo - 이후에 사업자 등록증 파일 구현


}
