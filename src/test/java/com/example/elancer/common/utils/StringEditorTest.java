package com.example.elancer.common.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class StringEditorTest {
    private final StringEditor stringEditor = new StringEditor();

    @DisplayName("문자열 리스트가 주어지면 ,로 구분되는 문자열이 된다.")
    @Test
    public void 문자열리스트가_문자열로() {
        //given
        List<String> strings = Arrays.asList("문자열1", "문자열2", "문자열3");

        //when
        String editedString = StringEditor.editStringListToString(strings);

        //then
        Assertions.assertThat(editedString).isEqualTo("문자열1,문자열2,문자열3");
    }

    @DisplayName("문자열이 주어지면 ,로 구분되 문자열 리스트가 된다.")
    @Test
    public void 문자열이_문자열리스트로() {
        //given
        String stirng = "문자열1,문자열2,문자열3";

        List<String> expectedStringList = Arrays.asList("문자열1", "문자열2", "문자열3");

        //when
        List<String> strings = StringEditor.editStringToStringList(stirng);

        //then
        Assertions.assertThat(strings.get(0)).isEqualTo("문자열1");
        Assertions.assertThat(strings.get(1)).isEqualTo("문자열2");
        Assertions.assertThat(strings.get(2)).isEqualTo("문자열3");
        Assertions.assertThat(strings).isEqualTo(expectedStringList);
    }
}