package com.example.elancer.freelancerprofile.repository.positionsearch;

import com.example.elancer.common.utils.PageUtil;
import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaDetailSkill;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingDetailSkill;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingSkill;
import com.example.elancer.freelancerprofile.model.position.publisher.QPublishingSkill;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.ListPath;
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
import static com.example.elancer.freelancerprofile.model.position.publisher.QPublisher.publisher;
import static com.example.elancer.freelancerprofile.model.position.publisher.QPublishingSkill.publishingSkill;

@Repository
@RequiredArgsConstructor
public class PublisherSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Publisher> searchPublishers(
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

        List<Publisher> publishers = jpaQueryFactory.selectFrom(publisher)
                .innerJoin(publisher.freelancerProfile, freelancerProfile).fetchJoin()
                .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
                .leftJoin(publisher.publishingSkills, publishingSkill)
                .distinct()
                .where(builder)
                .orderBy(publisher.num.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = PageUtil.isContentSizeGreaterThanPageSize(publishers, pageable);

        return new SliceImpl<Publisher>(hasNext ? PageUtil.subListLastContent(publishers, pageable) : publishers, pageable, hasNext);
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
            builder.or(publisher.etcSkill.containsIgnoreCase(majorSkillKeyword));

            if (Arrays.stream(PublishingDetailSkill.values()).anyMatch(publishingDetailSkill -> String.valueOf(publishingDetailSkill).equals(majorSkillKeyword.toUpperCase()))) {
                builder.or(publishingSkill.publishingDetailSkill.eq(PublishingDetailSkill.valueOf(majorSkillKeyword.toUpperCase())));
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

    public Slice<Publisher> searchPublishersByKeyword(String keyword, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        eqMajorSkillConds(keyword, builder);

        List<Publisher> publishers = jpaQueryFactory.selectFrom(publisher)
                .innerJoin(publisher.freelancerProfile, freelancerProfile).fetchJoin()
                .innerJoin(freelancerProfile.freelancer, freelancer).fetchJoin()
                .leftJoin(publisher.publishingSkills, publishingSkill)
                .distinct()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = PageUtil.isContentSizeGreaterThanPageSize(publishers, pageable);

        return new SliceImpl<Publisher>(hasNext ? PageUtil.subListLastContent(publishers, pageable) : publishers, pageable, hasNext);
    }
}
