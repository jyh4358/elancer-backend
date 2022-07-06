package com.example.elancer.freelancerprofile.repository.positionsearch;

import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.etc.QPositionEtc;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.elancer.freelancer.model.QFreelancer.freelancer;
import static com.example.elancer.freelancerprofile.model.QFreelancerProfile.freelancerProfile;
import static com.example.elancer.freelancerprofile.model.position.designer.QDesigner.designer;
import static com.example.elancer.freelancerprofile.model.position.etc.QPositionEtc.positionEtc;
import static com.example.elancer.freelancerprofile.model.position.publisher.QPublisher.publisher;

@Repository
@RequiredArgsConstructor
public class PositionEtcSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<PositionEtc> searchPositionEtc(
            PositionType positionType,
            List<String> majorSkillConditions,
            HopeWorkState hopeWorkState,
            PositionWorkManShip positionWorkManShip,
            WorkArea workArea,
            Pageable pageable
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(publisher.positionType.eq(positionType));
        eqMajorSkillConds(majorSkillConditions, builder);
        eqHopeWorkStateConds(hopeWorkState, builder);
        eqPositionWorkShipConds(positionWorkManShip, builder);
        eqWorkAreaConds(workArea, builder);

        QueryResults<PositionEtc> positionEtcQueryResults = jpaQueryFactory.selectFrom(positionEtc)
                .innerJoin(positionEtc.freelancerProfile, freelancerProfile).fetchJoin()
                .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
                .where(builder)
                .orderBy(positionEtc.num.desc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new SliceImpl<PositionEtc>(positionEtcQueryResults.getResults());
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
            builder.or(positionEtc.positionEtcField.containsIgnoreCase(majorSkillKeyword));
        }
    }

    private void eqHopeWorkStateConds(HopeWorkState hopeWorkState, BooleanBuilder builder) {
        if (hopeWorkState == null) {
            return;
        }

        if (hopeWorkState.equals(HopeWorkState.AT_HALF_COMPANY)) {
            builder.and(freelancer.freelancerAccountInfo.hopeWorkState.eq(HopeWorkState.AT_HOME))
                    .or(freelancer.freelancerAccountInfo.hopeWorkState.eq(HopeWorkState.AT_COMPANY));
            return;
        }

        builder.and(freelancer.freelancerAccountInfo.hopeWorkState.eq(hopeWorkState));
    }

    private void eqPositionWorkShipConds(PositionWorkManShip positionWorkManShip, BooleanBuilder builder) {
        if (positionWorkManShip == null) {
            return;
        }

        if (positionWorkManShip.equals(PositionWorkManShip.SENIOR)) {
            builder.and(freelancer.freelancerAccountInfo.careerYear.goe(positionWorkManShip.getYearInLine()));
            return;
        }

        builder.and(freelancer.freelancerAccountInfo.careerYear.between(positionWorkManShip.getYearInLine(), positionWorkManShip.getYearOutLine()));
    }
}
