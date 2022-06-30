package com.example.elancer.freelancerprofile.model.position.etc;

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
//@DiscriminatorValue("ETC")
public class PositionEtc extends Position {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "positionEtc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EtcRole> etcRoles = new ArrayList<>();

    private String positionEtcField;

    public PositionEtc(PositionType positionType, FreelancerProfile freelancerProfile) {
        super(positionType, freelancerProfile);
    }

    public static PositionEtc createBasicPositionEtc(PositionType positionType, FreelancerProfile freelancerProfile) {
        return new PositionEtc(positionType, freelancerProfile);
    }

    @Override
    public List<String> getAllSkillNames() {
        return etcRoles.stream()
                .map(etcRole -> etcRole.getEtcDetailRole().getDesc())
                .collect(Collectors.toList());
    }

    public void coverAllField(List<EtcRole> etcRoles, String positionEtcField) {
        coverEtcRoles(etcRoles);
        this.positionEtcField = positionEtcField;
    }

    private void coverEtcRoles(List<EtcRole> etcRoles) {
        this.etcRoles.clear();
        for (EtcRole etcRole : etcRoles) {
            etcRole.setPositionEtc(this);
        }
        this.etcRoles.addAll(etcRoles);
    }

}
