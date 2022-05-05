package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancerprofile.dto.DeveloperSkillsResponse;
import com.example.elancer.freelancerprofile.dto.ProfileEnumResponse;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
import com.example.elancer.freelancerprofile.model.career.CompanyPosition;
import com.example.elancer.freelancerprofile.model.language.LanguageAbility;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspDetailSkill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreelancerPositionEnumService {

    public DeveloperSkillsResponse findDeveloperSkillNames() {
        return new DeveloperSkillsResponse(
                Arrays.stream(JavaDetailSkill.values()).map(JavaDetailSkill::getDesc).collect(Collectors.toList()),
                Arrays.stream(MobileAppDetailSkill.values()).map(MobileAppDetailSkill::getDesc).collect(Collectors.toList()),
                Arrays.stream(PhpOrAspDetailSkill.values()).map(PhpOrAspDetailSkill::getDesc).collect(Collectors.toList()),
                Arrays.stream(DotNetDetailSkill.values()).map(DotNetDetailSkill::getDesc).collect(Collectors.toList()),
                Arrays.stream(JavaScriptDetailSkill.values()).map(JavaScriptDetailSkill::getDesc).collect(Collectors.toList()),
                Arrays.stream(CDetailSkill.values()).map(CDetailSkill::getDesc).collect(Collectors.toList()),
                Arrays.stream(DBDetailSkill.values()).map(DBDetailSkill::getDesc).collect(Collectors.toList())
        );
    }


    public ProfileEnumResponse findProfileEnumNames() {
        return new ProfileEnumResponse(
                Arrays.stream(SchoolLevel.values()).map(SchoolLevel::getDesc).collect(Collectors.toList()),
                Arrays.stream(AcademicState.values()).map(AcademicState::getDesc).collect(Collectors.toList()),
                Arrays.stream(CompanyPosition.values()).map(CompanyPosition::getDesc).collect(Collectors.toList()),
                Arrays.stream(LanguageAbility.values()).map(LanguageAbility::getDesc).collect(Collectors.toList())
        );
    }
}
