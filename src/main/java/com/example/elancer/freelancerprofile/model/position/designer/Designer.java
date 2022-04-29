package com.example.elancer.freelancerprofile.model.position.designer;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.Position;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DESIGNER")
public class Designer extends Position {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "designer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DesignRole> designRoles = new ArrayList<>();

    private String etcRole;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "designer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DesignSkill> designSkills = new ArrayList<>();

    private String etcSkill;

    public Designer(FreelancerProfile freelancerProfile) {
        super(freelancerProfile);
    }

    public static Designer createBasicDesigner(FreelancerProfile freelancerProfile) {
        return new Designer(freelancerProfile);
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
