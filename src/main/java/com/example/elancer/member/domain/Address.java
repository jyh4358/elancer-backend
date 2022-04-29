package com.example.elancer.member.domain;

import com.example.elancer.member.domain.CountryType;
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
    private String mainAddress;
    @NotBlank
    private String detailAddress;

    public Address(CountryType country, String zipcode, String mainAddress, String detailAddress) {
        this.country = country;
        this.zipcode = zipcode;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
    }
}

