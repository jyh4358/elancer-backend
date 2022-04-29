package com.example.elancer.freelancerprofile.model.position.etc;

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
@DiscriminatorValue("ETC")
public class PositionEtc extends Position {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "positionEtc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EtcRole> etcRoles = new ArrayList<>();

    private String positionEtcField;

    public PositionEtc(FreelancerProfile freelancerProfile) {
        super(freelancerProfile);
    }

    public static PositionEtc createBasicPositionEtc(FreelancerProfile freelancerProfile) {
        return new PositionEtc(freelancerProfile);
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
