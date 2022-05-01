package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailSkill;
import com.example.elancer.freelancerprofile.model.position.designer.DesignRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignSkill;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DesignerResponse {
    private List<DesignDetailRole> designDetailRoles;
    private String etcRole;
    private List<DesignDetailSkill> designDetailSkills;
    private String etcSkill;

    public static DesignerResponse of(Designer designer) {
        return new DesignerResponse(
                designer.getDesignRoles().stream()
                        .map(DesignRole::getDesignDetailRole)
                        .collect(Collectors.toList()),
                designer.getEtcRole(),
                designer.getDesignSkills().stream()
                        .map(DesignSkill::getDesignDetailSkill)
                        .collect(Collectors.toList()),
                designer.getEtcSkill()
        );
    }
}
