package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.dto.QFreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.elancer.freelancer.model.QFreelancer.freelancer;
import static com.example.elancer.freelancerprofile.model.QFreelancerProfile.freelancerProfile;
import static com.example.elancer.freelancerprofile.model.position.developer.QDeveloper.developer;

@Repository
@RequiredArgsConstructor
public class DeveloperSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Freelancer> findFreelancerProfileByFetch(
            PositionType positionType,
            List<String> majorSkillConditions,
            String minorSkill,
            List<HopeWorkState> hopeWorkStates,
            List<PositionWorkManShip> positionWorkManShips
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        eqMajorSkillConds(majorSkillConditions, builder);
        eqHopeWorkStateConds(hopeWorkStates, builder);
        eqPositionWorkShipConds(positionWorkManShips, builder);


        QueryResults<Freelancer> developerQueryResults = jpaQueryFactory.selectFrom(freelancer)
                        .innerJoin(developer.freelancerProfile, freelancerProfile).fetchJoin()
                        .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
                        .where(
                                developer.positionType.eq(positionType),
                                eqMinorSkills(minorSkill),
                                builder
                        )
                        .fetchResults();

        return new SliceImpl<Freelancer>(developerQueryResults.getResults());
    }

    private void eqMajorSkillConds(List<String> majorSkillKeywords, BooleanBuilder builder) {
        if (majorSkillKeywords == null) {
            return;
        }

        for (String majorSkillKeyword : majorSkillKeywords) {
            builder.and(developer.focusSkill.containsIgnoreCase(majorSkillKeyword));
        }
    }

    private void eqHopeWorkStateConds(List<HopeWorkState> hopeWorkStates, BooleanBuilder builder) {
        if (hopeWorkStates == null) {
            return;
        }

        for (HopeWorkState hopeWorkState : hopeWorkStates) {
            if (hopeWorkState.equals(HopeWorkState.AT_HALF_COMPANY)) {
                builder.and(freelancer.freelancerAccountInfo.hopeWorkState.eq(HopeWorkState.AT_HOME))
                        .and(freelancer.freelancerAccountInfo.hopeWorkState.eq(HopeWorkState.AT_COMPANY));
                continue;
            }

            builder.and(freelancer.freelancerAccountInfo.hopeWorkState.eq(hopeWorkState));
        }
    }

    private void eqPositionWorkShipConds(List<PositionWorkManShip> positionWorkManShips, BooleanBuilder builder) {
        if (positionWorkManShips == null) {
            return;
        }

        for (PositionWorkManShip positionWorkManShip : positionWorkManShips) {
            if (positionWorkManShip.equals(PositionWorkManShip.SENIOR)) {
                builder.and(freelancer.freelancerAccountInfo.careerYear.goe(positionWorkManShip.getYearInLine()));
                continue;
            }
            builder.and(freelancer.freelancerAccountInfo.careerYear.between(positionWorkManShip.getYearInLine(), positionWorkManShip.getYearOutLine()));
        }
    }

    private BooleanExpression eqMinorSkills(String minorSkill) {
        return developer.etcSkill.containsIgnoreCase(minorSkill);
    }

//    private List<BooleanExpression> eqMajorSkills(List<String> majorSkillKeywords) {
//        if (majorSkillKeywords.size() == 0 || majorSkillKeywords == null) {
//            return null;
//        }
//
//        List<BooleanExpression> tuples = new ArrayList<>();
//        for (String majorSkillKeyword : majorSkillKeywords) {
//            tuples.add(developer.focusSkill.containsIgnoreCase(majorSkillKeyword));
//        }
//
//        return tuples;
//    }
}
