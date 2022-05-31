package com.example.elancer.enterprise.repository;

import com.example.elancer.enterprise.model.enterpriseintro.SubBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubBusinessRepository extends JpaRepository<SubBusiness, String> {
    @Query("select sb from SubBusiness sb where sb.code in :codes")
    List<SubBusiness> findSubBusiness(@Param("codes") List<String> codes);
}
