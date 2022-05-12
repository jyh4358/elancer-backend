package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.repository.DeveloperSearchRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
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
            List<PositionWorkManShip> positionWorkManShips,
            WorkArea workArea,
            MemberDetails memberDetails
    ) {
        Slice<Developer> developers= developerSearchRepository.findFreelancerProfileByFetch(positionType, majorSkillKeywords, minorSkill, hopeWorkStates, positionWorkManShips, workArea.getDesc());
        // 1. 잘못생각 프로젝트 찜이 필요한게 아니라 기업의 인재스크랩이 필요함.


        // 2. developer에서 각 스킬들 뽑아서 List<String> 형태로 반환하는 메서드 만들어 줄것.
        // 3. 우선 MemberDetails를 통해 로그인한 사용자의 요청인지, 맞다면 사용자가 기업계정인지 확인. -> 맞다면 4번 진행,  아니라면 4번 진행필요 x
        // 4. 위의 개발자들과 인재스크랩 리스트를 돌며 인재스크랩의 freelancer와 develop.profile.freelancer가 같으면 응답dto의 필드에 true를 해주는 상황을 보여줘야 할듯 -> list.contain활용해볼것.
        // 5. developer 객체만을 이용해 응답 dto를 할수 있나? developer로 필요할 수도 있긴한데 별로 안좋은 방법인거 같다;;

    }
}
