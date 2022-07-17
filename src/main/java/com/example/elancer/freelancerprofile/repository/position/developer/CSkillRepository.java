package com.example.elancer.freelancerprofile.repository.position.developer;

import com.example.elancer.freelancerprofile.model.position.developer.cskill.ClangSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CSkillRepository extends JpaRepository<ClangSkill, Long> {
}
