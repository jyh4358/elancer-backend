package com.example.elancer.freelancerprofile.model.position.etc;

import com.example.elancer.common.model.BasicEntity;
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
public class EtcRole extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private EtcDetailRole etcDetailRole;

    @ManyToOne(fetch = FetchType.LAZY)
    private PositionEtc positionEtc;

    public EtcRole(EtcDetailRole etcDetailRole, PositionEtc positionEtc) {
        this.etcDetailRole = etcDetailRole;
        this.positionEtc = positionEtc;
    }

    public static EtcRole createEtcRole(EtcDetailRole etcDetailRole, PositionEtc positionEtc) {
        return new EtcRole(etcDetailRole, positionEtc);
    }

    public void setPositionEtc(PositionEtc positionEtc) {
        this.positionEtc = positionEtc;
    }
}
