package com.example.elancer.freelancerprofile.model.position;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@DiscriminatorValue("CROWD_WORKER")
public class CrowdWorker extends Position{

    public CrowdWorker(PositionType positionType, FreelancerProfile freelancerProfile) {
        super(positionType, freelancerProfile);
    }

    @Override
    List<String> getAllSkills() {
        return null;
    }
}
