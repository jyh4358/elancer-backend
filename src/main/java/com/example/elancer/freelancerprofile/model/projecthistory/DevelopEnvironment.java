package com.example.elancer.freelancerprofile.model.projecthistory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DevelopEnvironment {
    private String developEnvironmentModel;
    private String developEnvironmentOS;
    private String developEnvironmentLanguage;
    private String developEnvironmentDBName;
    private String developEnvironmentTool;
    private String developEnvironmentCommunication;
    private String developEnvironmentEtc;

    public static DevelopEnvironment of(
            String developEnvironmentModel,
            String developEnvironmentOS,
            String developEnvironmentLanguage,
            String developEnvironmentDBName,
            String developEnvironmentTool,
            String developEnvironmentCommunication,
            String developEnvironmentEtc
    ) {
        return new DevelopEnvironment(
                developEnvironmentModel,
                developEnvironmentOS,
                developEnvironmentLanguage,
                developEnvironmentDBName,
                developEnvironmentTool,
                developEnvironmentCommunication,
                developEnvironmentEtc
        );
    }
}
