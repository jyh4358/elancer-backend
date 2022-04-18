package com.example.elancer.enterprise.repository;

import com.example.elancer.enterprise.domain.MainBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainBusinessRepository extends JpaRepository<MainBusiness, String> {
    @Query("select mb from MainBusiness mb where mb.code in :codes")
    List<MainBusiness> findMainBusiness(@Param("codes") List<String> codes);
}
