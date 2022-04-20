package com.example.elancer.freelancerprofile.repository.academic;

import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicRepository extends JpaRepository<AcademicAbility, Long> {
    List<AcademicAbility> findByFreelancerProfileNum(Long profileNum);
    void deleteByFreelancerProfileNum(Long profileNum);
}
