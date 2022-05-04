package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancerprofile.model.language.Language;
import com.example.elancer.freelancerprofile.model.language.LanguageAbility;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LanguageResponse {
    private String languageName;
    private LanguageAbility languageAbility;
    private String languageAbilityDescription;

    public static LanguageResponse of(Language language) {
        return new LanguageResponse(language.getLanguageName(), language.getLanguageAbility(), language.getLanguageAbility().getDesc());
    }

    public static List<LanguageResponse> listOf(List<Language> languages) {
        return languages.stream()
                .map(LanguageResponse::of)
                .collect(Collectors.toList());
    }
}
