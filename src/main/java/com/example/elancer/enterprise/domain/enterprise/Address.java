package com.example.elancer.enterprise.domain.enterprise;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @NotBlank
    @Enumerated(EnumType.STRING)
    private CountryType country;
    @NotBlank
    private String zipcode;
    @NotBlank
    private String address1; // 주소
    @NotBlank
    private String address2; // 상세주소

    public Address(CountryType country, String zipcode, String address1, String address2) {
        this.country = country;
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
    }
}
