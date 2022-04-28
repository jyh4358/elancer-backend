package com.example.elancer.freelancerprofile.dto.request.position;

import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailSkill;
import com.example.elancer.freelancerprofile.model.position.designer.DesignRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignSkill;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DesignerCoverRequest {
    private List<DesignDetailRole> designDetailRoles;
    private String etcRole;
    private List<DesignDetailSkill> designDetailSkills;
    private String etcSkill;

    public List<DesignRole> toDesignRoles(Designer designer) {
        if (this.designDetailRoles == null) {
            return new ArrayList<>();
        }

        return this.designDetailRoles.stream()
                .map(designDetailRole -> DesignRole.createDesignRole(designDetailRole, designer))
                .collect(Collectors.toList());
    }

    public List<DesignSkill> toDesignSkills(Designer designer) {
        if (this.designDetailSkills == null) {
            return new ArrayList<>();
        }

        return this.designDetailSkills.stream()
                .map(designDetailSkill -> DesignSkill.createDesignSkill(designDetailSkill, designer))
                .collect(Collectors.toList());
    }
}
