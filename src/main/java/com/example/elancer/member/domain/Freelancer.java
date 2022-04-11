package com.example.elancer.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FREELANCER")
public class Freelancer extends Member{

    private String testFree;

    public Freelancer(String testFree) {
        this.testFree = testFree;
    }
}
