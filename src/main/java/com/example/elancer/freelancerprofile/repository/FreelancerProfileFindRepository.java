package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.QFreelancerProfile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.elancer.freelancerprofile.model.QFreelancerProfile.freelancerProfile;

@Repository
@RequiredArgsConstructor
public class FreelancerProfileFindRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<FreelancerProfile> findFreelancerProfileByFetch(Long freelancerNum) {
        FreelancerProfile freelancerProfileByFetch = jpaQueryFactory.selectFrom(QFreelancerProfile.freelancerProfile)
                .where(QFreelancerProfile.freelancerProfile.freelancer.num.eq(freelancerNum))
                .fetchOne();
        return Optional.ofNullable(freelancerProfileByFetch);
    }
}
