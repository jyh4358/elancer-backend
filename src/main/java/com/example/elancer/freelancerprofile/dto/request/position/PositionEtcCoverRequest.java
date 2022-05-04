package com.example.elancer.freelancerprofile.dto.request.position;

import com.example.elancer.common.validatemessages.PositionRequestMessages;
import com.example.elancer.freelancerprofile.model.position.etc.EtcDetailRole;
import com.example.elancer.freelancerprofile.model.position.etc.EtcRole;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PositionEtcCoverRequest {

    @Size(max = 3)
    @NotNull(message = PositionRequestMessages.POSITION_ETC_ROLES_NULL_MESSAGE)
    private List<EtcDetailRole> etcDetailRoles;
    private String positionEtcRole;

    public List<EtcRole> toEtcRole(PositionEtc positionEtc) {
        if (this.etcDetailRoles == null) {
            return new ArrayList<>();
        }

        return this.etcDetailRoles.stream()
                .map(etcDetailRole -> EtcRole.createEtcRole(etcDetailRole, positionEtc))
                .collect(Collectors.toList());
    }
}
