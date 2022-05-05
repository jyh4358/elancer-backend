package com.example.elancer.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CountryTypeTest {

    @DisplayName("국가이름 목록을 출력할 수 있다.")
    @Test
    public void 국가이름_출력() {
        //when
        List<String> collect = Arrays.stream(CountryType.values()).map(CountryType::getName).collect(Collectors.toList());
        System.out.println(collect);

        //then
        Assertions.assertThat(collect).hasSize(5);
        Assertions.assertThat(collect.get(0)).isEqualTo(CountryType.KR.getName());
        Assertions.assertThat(collect.get(1)).isEqualTo(CountryType.UK.getName());
        Assertions.assertThat(collect.get(2)).isEqualTo(CountryType.US.getName());
        Assertions.assertThat(collect.get(3)).isEqualTo(CountryType.JP.getName());
        Assertions.assertThat(collect.get(4)).isEqualTo(CountryType.CN.getName());
    }
}