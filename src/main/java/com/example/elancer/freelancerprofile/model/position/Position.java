package com.example.elancer.freelancerprofile.model.position;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "position_type")
public abstract class Position extends BasicEntity {

    @NotNull
    private PositionType positionType;

    @OneToOne(fetch = FetchType.LAZY)
    private FreelancerProfile freelancerProfile;

    public Position(PositionType positionType, FreelancerProfile freelancerProfile) {
        this.positionType = positionType;
        this.freelancerProfile = freelancerProfile;
    }
}
