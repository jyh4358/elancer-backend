package com.example.elancer.member.repository;

import com.example.elancer.member.domain.Enterprise;
import com.example.elancer.member.domain.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

}
