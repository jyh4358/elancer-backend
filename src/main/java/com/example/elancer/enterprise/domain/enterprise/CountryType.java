package com.example.elancer.enterprise.domain.enterprise;

public enum CountryType {

    KR("대한민국"),
    UK("영국"),
    US("미국"),
    JP("일본"),
    CN("중국");

    final private String name;
    CountryType(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
