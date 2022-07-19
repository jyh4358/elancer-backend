package com.example.elancer.freelancerprofile.repository.positionsearch;

import com.example.elancer.common.utils.PageUtil;
import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspDetailSkill;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import static com.example.elancer.freelancerprofile.model.position.developer.QDeveloper.developer;
import static com.example.elancer.freelancerprofile.model.position.developer.cskill.QClangSkill.clangSkill;
import static com.example.elancer.freelancerprofile.model.position.developer.dbskill.QDBSkill.dBSkill;
import static com.example.elancer.freelancerprofile.model.position.developer.dotnet.QDotNetSkill.dotNetSkill;
import static com.example.elancer.freelancerprofile.model.position.developer.javascript.QJavaScriptSkill.javaScriptSkill;
import static com.example.elancer.freelancerprofile.model.position.developer.javaskill.QJavaSkill.javaSkill;
import static com.example.elancer.freelancerprofile.model.position.developer.mobileskill.QMobileAppSkill.mobileAppSkill;
import static com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.QPhpOrAspSkill.phpOrAspSkill;

@Repository
@RequiredArgsConstructor
public class DeveloperSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Developer> searchDevelopers(
            PositionType positionType,
            String majorSkillConditions,
            HopeWorkState hopeWorkState,
            PositionWorkManShip positionWorkManShip,
            Pageable pageable,
            WorkArea workArea
    ) {
        BooleanBuilder builder = new BooleanBuilder();

//        builder.and(developer.positionType.eq(positionType));
        eqMajorSkillConds(majorSkillConditions, builder);
        eqHopeWorkStateConds(hopeWorkState, builder);
        eqPositionWorkShipConds(positionWorkManShip, builder);
        eqWorkAreaConds(workArea, builder);

        List<Developer> developers = jpaQueryFactory.selectFrom(developer)
                .innerJoin(developer.freelancerProfile, freelancerProfile).fetchJoin()
                .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
                .leftJoin(developer.javaSkills, javaSkill)
                .leftJoin(developer.mobileAppSkills, mobileAppSkill)
                .leftJoin(developer.phpOrAspSkills, phpOrAspSkill)
                .leftJoin(developer.dotNetSkills, dotNetSkill)
                .leftJoin(developer.javaScriptSkills, javaScriptSkill)
                .leftJoin(developer.cSkills, clangSkill)
                .leftJoin(developer.dbSkills, dBSkill)
                .distinct()
                .where(builder)
//                .orderBy(developer.num.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = PageUtil.isContentSizeGreaterThanPageSize(developers, pageable);

        return new SliceImpl<Developer>(hasNext ? PageUtil.subListLastContent(developers, pageable) : developers, pageable, hasNext);
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
            builder.or(developer.focusSkill.containsIgnoreCase(majorSkillKeyword)).or(developer.etcSkill.containsIgnoreCase(majorSkillKeyword));

            if (Arrays.stream(JavaDetailSkill.values()).anyMatch(javaDetailSkill -> String.valueOf(javaDetailSkill).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(javaSkill.javaDetailSkill.eq(JavaDetailSkill.valueOf(majorSkillKeyword.toUpperCase())));
            }
            if (Arrays.stream(MobileAppDetailSkill.values()).anyMatch(mobileAppDetailSkill -> String.valueOf(mobileAppDetailSkill).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(mobileAppSkill.mobileAppDetailSkill.eq(MobileAppDetailSkill.valueOf(majorSkillKeyword.toUpperCase())));
            }
            if (Arrays.stream(PhpOrAspDetailSkill.values()).anyMatch(phpOrAspDetailSkill -> String.valueOf(phpOrAspDetailSkill).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(phpOrAspSkill.phpOrAspDetailSkill.eq(PhpOrAspDetailSkill.valueOf(majorSkillKeyword.toUpperCase())));
            }
            if (Arrays.stream(DotNetDetailSkill.values()).anyMatch(dotNetDetailSkill -> String.valueOf(dotNetDetailSkill).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(dotNetSkill.dotNetDetailSkill.eq(DotNetDetailSkill.valueOf(majorSkillKeyword.toUpperCase())));
            }
            if (Arrays.stream(JavaScriptDetailSkill.values()).anyMatch(javaScriptDetailSkill -> String.valueOf(javaScriptDetailSkill).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(javaScriptSkill.javaScriptDetailSkill.eq(JavaScriptDetailSkill.valueOf(majorSkillKeyword.toUpperCase())));
            }
            if (Arrays.stream(CDetailSkill.values()).anyMatch(cDetailSkill -> String.valueOf(cDetailSkill).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(clangSkill.cDetailSkill.eq(CDetailSkill.valueOf(majorSkillKeyword.toUpperCase())));
            }
            if (Arrays.stream(DBDetailSkill.values()).anyMatch(dbDetailSkill -> String.valueOf(dbDetailSkill).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(dBSkill.dbDetailSkill.eq(DBDetailSkill.valueOf(majorSkillKeyword.toUpperCase())));
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

    public Slice<Developer> searchDevelopersByKeyword(String keyword, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        eqMajorSkillConds(keyword, builder);

        List<Developer> developers = jpaQueryFactory.selectFrom(developer)
                .innerJoin(developer.freelancerProfile, freelancerProfile).fetchJoin()
                .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
                .leftJoin(developer.javaSkills, javaSkill)
                .leftJoin(developer.mobileAppSkills, mobileAppSkill)
                .leftJoin(developer.phpOrAspSkills, phpOrAspSkill)
                .leftJoin(developer.dotNetSkills, dotNetSkill)
                .leftJoin(developer.javaScriptSkills, javaScriptSkill)
                .leftJoin(developer.cSkills, clangSkill)
                .leftJoin(developer.dbSkills, dBSkill)
                .distinct()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = PageUtil.isContentSizeGreaterThanPageSize(developers, pageable);

        return new SliceImpl<Developer>(hasNext ? PageUtil.subListLastContent(developers, pageable) : developers, pageable, hasNext);
    }
}
