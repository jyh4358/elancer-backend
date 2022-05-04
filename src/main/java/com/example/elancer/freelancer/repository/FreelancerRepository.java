package com.example.elancer.freelancer.repository;

import com.example.elancer.freelancer.model.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    boolean existsByUserId(String userId);
//    Optional<Freelancer> findByUserId(String userId);
}
