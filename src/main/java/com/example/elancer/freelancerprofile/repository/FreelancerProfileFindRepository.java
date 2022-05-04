package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.QFreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.QAcademicAbility;
import com.example.elancer.freelancerprofile.model.career.QCareer;
import com.example.elancer.freelancerprofile.model.education.QEducation;
import com.example.elancer.freelancerprofile.model.language.QLanguage;
import com.example.elancer.freelancerprofile.model.license.QLicense;
import com.example.elancer.freelancerprofile.model.projecthistory.QProjectHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.elancer.freelancerprofile.model.QFreelancerProfile.freelancerProfile;
import static com.example.elancer.freelancerprofile.model.academic.QAcademicAbility.academicAbility;
import static com.example.elancer.freelancerprofile.model.career.QCareer.career;
import static com.example.elancer.freelancerprofile.model.education.QEducation.education;
import static com.example.elancer.freelancerprofile.model.language.QLanguage.language;
import static com.example.elancer.freelancerprofile.model.license.QLicense.license;
import static com.example.elancer.freelancerprofile.model.projecthistory.QProjectHistory.projectHistory;

@Repository
@RequiredArgsConstructor
public class FreelancerProfileFindRepository {
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Multibag 해결을 못함... 배치사이즈 설정 주는데 왜 적용이 안될까;
     * */

    public Optional<FreelancerProfile> findFreelancerProfileByFetch(Long freelancerNum) {
        FreelancerProfile freelancerProfileByFetch = jpaQueryFactory.selectFrom(freelancerProfile)
                .leftJoin(freelancerProfile.academicAbilities, academicAbility).fetchJoin()
                .leftJoin(freelancerProfile.careers, career)/*.fetchJoin()*/
                .leftJoin(freelancerProfile.educations, education)/*.fetchJoin()*/
                .leftJoin(freelancerProfile.licenses, license)/*.fetchJoin()*/
                .leftJoin(freelancerProfile.languages, language)/*.fetchJoin()*/
                .leftJoin(freelancerProfile.projectHistories, projectHistory)/*.fetchJoin()*/
                .where(freelancerProfile.freelancer.num.eq(freelancerNum))
                .fetchFirst();
        return Optional.ofNullable(freelancerProfileByFetch);
    }
}
