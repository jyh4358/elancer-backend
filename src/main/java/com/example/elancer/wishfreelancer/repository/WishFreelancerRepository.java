package com.example.elancer.wishfreelancer.repository;

import com.example.elancer.wishfreelancer.model.WishFreelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishFreelancerRepository extends JpaRepository<WishFreelancer, Long> {

    @Query("delete from WishFreelancer w where w.enterprise = :enterpriseNum and w.freelancer = :freelancerNum")
    void deleteWithScrap(@Param("enterpriseNum") Long enterpriseNum, @Param("freelancerNum") Long freelancerNum);
}
