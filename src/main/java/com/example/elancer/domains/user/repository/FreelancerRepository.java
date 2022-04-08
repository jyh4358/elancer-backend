package com.example.elancer.domains.user.repository;

import com.example.elancer.domains.user.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer, String> {

}
