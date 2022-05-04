package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancerprofile.model.position.etc.EtcDetailRole;
import com.example.elancer.freelancerprofile.model.position.etc.EtcRole;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PositionEtcResponse {
    private List<EtcDetailRole> etcDetailRoles;
    private String positionEtcRole;

    public static PositionEtcResponse of(PositionEtc positionEtc) {
        return new PositionEtcResponse(
                positionEtc.getEtcRoles().stream()
                        .map(EtcRole::getEtcDetailRole)
                        .collect(Collectors.toList()),
                positionEtc.getPositionEtcField()
        );
    }
}
