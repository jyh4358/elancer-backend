package com.example.elancer.freelancerprofile.model.position.designer;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Designer extends Position {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "designer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DesignRole> designRoles = new ArrayList<>();

    private String etcRole;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "designer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DesignSkill> designSkills = new ArrayList<>();

    private String etcSkill;

    public Designer(PositionType positionType, FreelancerProfile freelancerProfile) {
        super(positionType, freelancerProfile);
    }

    public static Designer createBasicDesigner(PositionType positionType, FreelancerProfile freelancerProfile) {
        return new Designer(positionType, freelancerProfile);
    }

    @Override
    public List<String> getAllSkillNames() {
        List<String> designSkillNames = new ArrayList<>();
        designSkillNames.addAll(designRoles.stream()
                .map(designRole -> designRole.getDesignDetailRole().getDesc())
                .collect(Collectors.toList()));
        designSkillNames.add(etcRole);

        designSkillNames.addAll(designSkills.stream()
                .map(designSkill -> designSkill.getDesignDetailSkill().getDesc())
                .collect(Collectors.toList()));
        designSkillNames.add(etcSkill);

        return designSkillNames;
    }

    public void coverDesignRoleAndSkill(List<DesignRole> designRoles, List<DesignSkill> designSkills, String etcRole,  String etcSkill) {
        coverDesignRoles(designRoles);
        coverDesignSkills(designSkills);
        this.etcRole = etcRole;
        this.etcSkill = etcSkill;
    }

    private void coverDesignRoles(List<DesignRole> designRoles) {
        this.designRoles.clear();
        for (DesignRole designRole : designRoles) {
            designRole.setDesigner(this);
        }
        this.designRoles.addAll(designRoles);
    }

    private void coverDesignSkills(List<DesignSkill> designSkills) {
        this.designSkills.clear();
        for (DesignSkill designSkill : designSkills) {
            designSkill.setDesigner(this);
        }
        this.designSkills.addAll(designSkills);
    }
}
