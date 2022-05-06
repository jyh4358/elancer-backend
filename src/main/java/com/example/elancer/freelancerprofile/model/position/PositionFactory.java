package com.example.elancer.freelancerprofile.model.position;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;

public class PositionFactory {

    public static Developer generateDeveloper(FreelancerProfile freelancerProfile) {
        return Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, null, null);
    }

    public static Publisher generatePublisher(FreelancerProfile freelancerProfile) {
        return Publisher.createBasicPublisher(PositionType.PUBLISHER, freelancerProfile, null);
    }

    public static Designer generateDesigner(FreelancerProfile freelancerProfile) {
        return Designer.createBasicDesigner(PositionType.DESIGNER, freelancerProfile);
    }

    public static Planner generatePlanner(FreelancerProfile freelancerProfile) {
        return Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile);
    }

    public static CrowdWorker generateCrowdWorker(FreelancerProfile freelancerProfile) {
        return new CrowdWorker(PositionType.CROWD_WORKER, freelancerProfile);
    }

    public static PositionEtc generatePositionEtc(FreelancerProfile freelancerProfile) {
        return PositionEtc.createBasicPositionEtc(PositionType.ETC, freelancerProfile);
    }
}
