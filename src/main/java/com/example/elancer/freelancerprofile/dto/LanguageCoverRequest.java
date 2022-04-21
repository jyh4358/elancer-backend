package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancerprofile.model.language.LanguageAbility;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LanguageCoverRequest {
    private String languageName;
    private LanguageAbility languageAbility;
}
