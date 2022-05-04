package com.example.elancer.freelancerprofile.model.language;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Language extends BasicEntity {
    private String languageName;
    @Enumerated(EnumType.STRING)
    private LanguageAbility languageAbility;

    @ManyToOne(fetch = FetchType.LAZY)
    private FreelancerProfile freelancerProfile;

    public Language(String languageName, LanguageAbility languageAbility) {
        this.languageName = languageName;
        this.languageAbility = languageAbility;
    }

    public static Language createLanguage(String languageName, LanguageAbility languageAbility) {
        return new Language(languageName, languageAbility);
    }

    public void setFreelancerProfile(FreelancerProfile freelancerProfile) {
        this.freelancerProfile = freelancerProfile;
    }
}
