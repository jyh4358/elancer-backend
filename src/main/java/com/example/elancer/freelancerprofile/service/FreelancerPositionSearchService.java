package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.repository.DeveloperSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreelancerPositionSearchService {
    private final DeveloperSearchRepository developerSearchRepository;
//    private final Proje developerSearchRepository;

    @Transactional(readOnly = true)
    public void searchDevelopers(
            PositionType positionType,
            List<String> majorSkillKeywords,
            String minorSkill,
            List<HopeWorkState> hopeWorkStates,
            List<PositionWorkManShip> positionWorkManShips,
            WorkArea workArea
    ) {
        Slice<Developer> freelancerProfileByFetch = developerSearchRepository.findFreelancerProfileByFetch(positionType, majorSkillKeywords, minorSkill, hopeWorkStates, positionWorkManShips, workArea.getDesc());
        // 1. 프로젝트 찜 리스트 해당 요청자 넘버로 요청. -> wishProjectRepo에 프리랜서 넘버로 조회하는 메서드 구현
        // 2.
//        List<Developer> developers = freelancerProfileByFetch.getContent();
//        developers.stream()
//                .map(developer -> developer.)
        // 3. developer에서 각 스킬들 뽑아서 List<String> 형태로 반환하는 메서드 만들어 줄것.

    }
}
