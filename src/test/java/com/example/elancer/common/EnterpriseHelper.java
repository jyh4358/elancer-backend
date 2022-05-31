package com.example.elancer.common;

import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.domain.MemberType;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EnterpriseHelper {

    public static Enterprise 기업_생성(EnterpriseRepository enterpriseRepository, PasswordEncoder passwordEncoder) {
        Enterprise enterprise = Enterprise.builder()
                .userId("enterpriseId")
                .password(passwordEncoder.encode("pwd"))
                .name("회원이름")
                .phone("000-0000-0000")
                .email("enterprise@gmail.com")
                .website("www.enterprise.com")
                .address(new Address(CountryType.KR, "zipcode", "address1", "address2"))
                .role(MemberType.ENTERPRISE)
                .companyName("기업명")
                .companyPeople(10)
                .position("사장")
                .telNumber("000-1111-1111")
                .bizContents("SI")
                .sales(100000000)
                .idNumber("123-123-123")
                .build();

        return enterpriseRepository.save(enterprise);
    }
}
