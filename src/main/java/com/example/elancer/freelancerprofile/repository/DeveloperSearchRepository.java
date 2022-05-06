package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.elancer.freelancer.model.QFreelancer.freelancer;
import static com.example.elancer.freelancerprofile.model.QFreelancerProfile.freelancerProfile;
import static com.example.elancer.freelancerprofile.model.position.developer.QDeveloper.developer;

@Repository
@RequiredArgsConstructor
public class DeveloperSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<FreelancerSimpleResponse> findFreelancerProfileByFetch(
            PositionType positionType,
            List<String> majorSkillKeywords,
            String minorSkill,
            List<HopeWorkState> hopeWorkStates,
            List<PositionWorkManShip> positionWorkManShips
    ) {
//        jpaQueryFactory.selectFrom(developer)
//                .innerJoin(developer.freelancerProfile, freelancerProfile).fetchJoin()
//                .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
//                .where(
//                        developer.positionType.eq(positionType),
//                        eqMajorSkills(majorSkillKeywords),
//                        eqMinorSkills(minorSkill),
//
//
//                )
//                .fetchResults();
        return null;
    }

    private List<BooleanExpression> eqMajorSkills(List<String> majorSkillKeywords) {
        if (majorSkillKeywords.size() == 0 || majorSkillKeywords == null) {
            return null;
        }

        List<BooleanExpression> tuples = new ArrayList<>();
        for (String majorSkillKeyword : majorSkillKeywords) {
            tuples.add(developer.focusSkill.containsIgnoreCase(majorSkillKeyword));
        }

        return tuples;
    }

    private BooleanExpression eqMinorSkills(String minorSkill) {
        return developer.etcSkill.containsIgnoreCase(minorSkill);
    }
}
