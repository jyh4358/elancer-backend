package com.example.elancer.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("ENTERPRISE")
public class Enterprise extends Member{

    private String testEnter;

    public Enterprise(String testEnter) {
        this.testEnter = testEnter;
    }
}
