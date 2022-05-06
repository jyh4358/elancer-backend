package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.repository.DeveloperSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreelancerPositionSearchService {
    private final DeveloperSearchRepository developerSearchRepository;

    @Transactional(readOnly = true)
    public void searchDevelopers(
            PositionType positionType,
            List<String> majorSkillKeywords,
            String minorSkill,
            List<HopeWorkState> hopeWorkStates,
            List<PositionWorkManShip> positionWorkManShips
    ) {
        developerSearchRepository.findFreelancerProfileByFetch(positionType, majorSkillKeywords, minorSkill, hopeWorkStates, positionWorkManShips);
    }
}
