package com.example.elancer.enterprise.repository;

import com.example.elancer.enterprise.domain.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    boolean existsByUserId(String userId);

    Optional<Enterprise> findByUserId(String userId);
}
