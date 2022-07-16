package com.example.elancer.wishfreelancer.repository;

import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.wishfreelancer.model.WishFreelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishFreelancerRepository extends JpaRepository<WishFreelancer, Long> {

    @Query("delete from WishFreelancer w where w.freelancer.num = :freelancerNum")
    void deleteWithScrap( Long freelancerNum);

    List<WishFreelancer> findByEnterpriseNum(@Param("enter_num") Long enterpriseNum);
    Optional<WishFreelancer> findByFreelancerNum(@Param("free_num") Long freelancerNum);
}
