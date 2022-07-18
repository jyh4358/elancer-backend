package com.example.elancer.freelancerprofile.repository.positionsearch;

import com.example.elancer.common.utils.PageUtil;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailSkill;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.querydsl.core.BooleanBuilder;
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
import static com.example.elancer.freelancerprofile.model.position.designer.QDesignRole.designRole;
import static com.example.elancer.freelancerprofile.model.position.designer.QDesignSkill.designSkill;
import static com.example.elancer.freelancerprofile.model.position.designer.QDesigner.designer;

@Repository
@RequiredArgsConstructor
public class DesignerSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Designer> searchDesigners(
            PositionType positionType,
            List<String> majorSkillConditions,
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

        List<Designer> designers = jpaQueryFactory.selectFrom(designer)
                .innerJoin(designer.freelancerProfile, freelancerProfile).fetchJoin()
                .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
                .leftJoin(designer.designRoles, designRole)
                .leftJoin(designer.designSkills, designSkill)
                .distinct()
                .where(builder)
                .orderBy(designer.num.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = PageUtil.isContentSizeGreaterThanPageSize(designers, pageable);

        return new SliceImpl<Designer>(hasNext ? PageUtil.subListLastContent(designers, pageable) : designers, pageable, hasNext);
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
            builder.or(designer.etcSkill.containsIgnoreCase(majorSkillKeyword)).or(designer.etcRole.containsIgnoreCase(majorSkillKeyword));

            if (Arrays.stream(DesignDetailRole.values()).anyMatch(designDetailRole -> String.valueOf(designDetailRole).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(designRole.designDetailRole.eq(DesignDetailRole.valueOf(majorSkillKeyword.toUpperCase())));
            }

            if (Arrays.stream(DesignDetailSkill.values()).anyMatch(designDetailSkill -> String.valueOf(designDetailSkill).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(designSkill.designDetailSkill.eq(DesignDetailSkill.valueOf(majorSkillKeyword.toUpperCase())));
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
}
