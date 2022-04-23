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
    private List<DesignRole> designRole = new ArrayList<>();

    private String etcRole;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "designer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DesignSkill> designSkills = new ArrayList<>();

    private String etcSkill;

    public Designer(FreelancerProfile freelancerProfile, List<DesignRole> designRole, String etcRole, List<DesignSkill> designSkills, String etcSkill) {
        super(freelancerProfile);
        this.designRole = designRole;
        this.etcRole = etcRole;
        this.designSkills = designSkills;
        this.etcSkill = etcSkill;
    }
}
