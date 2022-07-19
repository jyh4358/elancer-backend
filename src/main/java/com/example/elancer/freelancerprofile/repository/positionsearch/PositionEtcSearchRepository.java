package com.example.elancer.freelancerprofile.repository.positionsearch;

import com.example.elancer.common.utils.PageUtil;
import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.etc.EtcDetailRole;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.etc.QEtcRole;
import com.example.elancer.freelancerprofile.model.position.etc.QPositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerDetailField;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

import static com.example.elancer.freelancer.model.QFreelancer.freelancer;
import static com.example.elancer.freelancerprofile.model.QFreelancerProfile.freelancerProfile;
import static com.example.elancer.freelancerprofile.model.position.designer.QDesigner.designer;
import static com.example.elancer.freelancerprofile.model.position.etc.QEtcRole.etcRole;
import static com.example.elancer.freelancerprofile.model.position.etc.QPositionEtc.positionEtc;
import static com.example.elancer.freelancerprofile.model.position.planner.QPlanner.planner;
import static com.example.elancer.freelancerprofile.model.position.planner.QPlannerField.plannerField;
import static com.example.elancer.freelancerprofile.model.position.publisher.QPublisher.publisher;

@Repository
@RequiredArgsConstructor
public class PositionEtcSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<PositionEtc> searchPositionEtc(
            PositionType positionType,
            String majorSkillConditions,
            HopeWorkState hopeWorkState,
            PositionWorkManShip positionWorkManShip,
            WorkArea workArea,
            Pageable pageable
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        eqMajorSkillConds(majorSkillConditions, builder);
        eqHopeWorkStateConds(hopeWorkState, builder);
        eqPositionWorkShipConds(positionWorkManShip, builder);
        eqWorkAreaConds(workArea, builder);

        List<PositionEtc> positionEtcs = jpaQueryFactory.selectFrom(positionEtc)
                .innerJoin(positionEtc.freelancerProfile, freelancerProfile).fetchJoin()
                .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
                .leftJoin(positionEtc.etcRoles, etcRole)
                .distinct()
                .where(builder)
                .orderBy(positionEtc.num.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = PageUtil.isContentSizeGreaterThanPageSize(positionEtcs, pageable);

        return new SliceImpl<PositionEtc>(hasNext ? PageUtil.subListLastContent(positionEtcs, pageable) : positionEtcs, pageable, hasNext);
    }

    private void eqWorkAreaConds(WorkArea area, BooleanBuilder builder) {
        if (area == null) {
            return;
        }

        builder.and(freelancer.address.mainAddress.containsIgnoreCase(area.getDesc()));
    }

    private void eqMajorSkillConds(String majorSkillKeywords, BooleanBuilder builder) {
        if (majorSkillKeywords == null || majorSkillKeywords.isEmpty() || majorSkillKeywords.isBlank()) {
            return;
        }

        List<String> tempMajorSkillKeywords = StringEditor.editStringToStringList(majorSkillKeywords);

        for (String majorSkillKeyword : tempMajorSkillKeywords) {
            builder.or(positionEtc.positionEtcField.containsIgnoreCase(majorSkillKeyword));

            if (Arrays.stream(EtcDetailRole.values()).anyMatch(etcDetailRole -> String.valueOf(etcDetailRole).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(etcRole.etcDetailRole.eq(EtcDetailRole.valueOf(majorSkillKeyword.toUpperCase())));
            }
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

    public Slice<PositionEtc> searchPositionEtcsByKeyword(String keyword, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        eqMajorSkillConds(keyword, builder);

        List<PositionEtc> positionEtcs = jpaQueryFactory.selectFrom(positionEtc)
                .innerJoin(positionEtc.freelancerProfile, freelancerProfile).fetchJoin()
                .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
                .leftJoin(positionEtc.etcRoles, etcRole)
                .distinct()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = PageUtil.isContentSizeGreaterThanPageSize(positionEtcs, pageable);

        return new SliceImpl<PositionEtc>(hasNext ? PageUtil.subListLastContent(positionEtcs, pageable) : positionEtcs, pageable, hasNext);
    }
}
