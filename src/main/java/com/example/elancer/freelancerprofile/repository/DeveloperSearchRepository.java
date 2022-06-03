package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.QPosition;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.member.domain.QAddress;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.elancer.freelancer.model.QFreelancer.freelancer;
import static com.example.elancer.freelancerprofile.model.QFreelancerProfile.freelancerProfile;
import static com.example.elancer.freelancerprofile.model.position.QPosition.position;
import static com.example.elancer.freelancerprofile.model.position.developer.QDeveloper.developer;

@Repository
@RequiredArgsConstructor
public class DeveloperSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Developer> findFreelancerProfileByFetch(
            PositionType positionType,
            List<String> majorSkillConditions,
            String minorSkill,
            List<HopeWorkState> hopeWorkStates,
            List<PositionWorkManShip> positionWorkManShips,
            WorkArea workArea
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(developer.positionType.eq(positionType));
        eqMinorSkills(minorSkill, builder);
        eqMajorSkillConds(majorSkillConditions, builder);
        eqHopeWorkStateConds(hopeWorkStates, builder);
        eqPositionWorkShipConds(positionWorkManShips, builder);
        eqWorkAreaConds(workArea, builder);

        QueryResults<Developer> developerQueryResults = jpaQueryFactory.selectFrom(developer)
                .innerJoin(developer.freelancerProfile, freelancerProfile).fetchJoin()
                .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
                .where(builder)
                .fetchResults();

        return new SliceImpl<Developer>(developerQueryResults.getResults());
    }

    private void eqWorkAreaConds(WorkArea area, BooleanBuilder builder) {
        if (area == null) {
            return;
        }

        builder.and(freelancer.address.mainAddress.containsIgnoreCase(area.getDesc()));
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

//        for (int i =0; i < hopeWorkStates.size(); i++) {
//            if (hopeWorkStates.get(i).equals(HopeWorkState.AT_HALF_COMPANY)) {
//                builder.and(freelancer.freelancerAccountInfo.hopeWorkState.eq(HopeWorkState.AT_HOME))
//                        .or(freelancer.freelancerAccountInfo.hopeWorkState.eq(HopeWorkState.AT_COMPANY));
//                continue;
//            }
//
//            if (i == 0) {
//                builder.and(freelancer.freelancerAccountInfo.hopeWorkState.eq(hopeWorkStates.get(i)));
//                continue;
//            }
//            builder.or(freelancer.freelancerAccountInfo.hopeWorkState.eq(hopeWorkStates.get(i)));
//        }
        int count = 0;
        for (HopeWorkState hopeWorkState : hopeWorkStates) {
            if (hopeWorkState.equals(HopeWorkState.AT_HALF_COMPANY)) {
                builder.and(freelancer.freelancerAccountInfo.hopeWorkState.eq(HopeWorkState.AT_HOME))
                        .or(freelancer.freelancerAccountInfo.hopeWorkState.eq(HopeWorkState.AT_COMPANY));
                continue;
            }

            if (count == 0) {
                builder.and(freelancer.freelancerAccountInfo.hopeWorkState.eq(hopeWorkState));
                count++;
                continue;
            }

            builder.or(freelancer.freelancerAccountInfo.hopeWorkState.eq(hopeWorkState));
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

    private void eqMinorSkills(String minorSkill, BooleanBuilder booleanBuilder) {
        if (minorSkill == null) {
            return;
        }

        booleanBuilder.and(developer.etcSkill.containsIgnoreCase(minorSkill));
//        return developer.etcSkill.containsIgnoreCase(minorSkill);

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
