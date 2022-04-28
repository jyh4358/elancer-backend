package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.QFreelancerProfile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.elancer.freelancerprofile.model.QFreelancerProfile.freelancerProfile;

@Repository
@RequiredArgsConstructor
public class FreelancerProfileFindRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public FreelancerProfile findFreelancerProfileByFetch(Long freelancerNum) {
        return jpaQueryFactory.selectFrom(freelancerProfile)
                .where(freelancerProfile.freelancer.num.eq(freelancerNum))
                .fetchOne();
    }
}
