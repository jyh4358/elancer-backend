package com.example.elancer.enterprise.repository;

import com.example.elancer.enterprise.model.enterprise.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    boolean existsByUserId(String userId);

    Optional<Enterprise> findById(Long num);

    @Query("select e from Enterprise e left join fetch e.heartScraps where e.num=: num")
    Optional<Enterprise> findScrapAndFreelancerFetchJoin(@Param("num") Long num);

}
