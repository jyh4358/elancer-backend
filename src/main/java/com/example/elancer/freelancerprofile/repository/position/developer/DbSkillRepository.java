package com.example.elancer.freelancerprofile.repository.position.developer;

import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbSkillRepository extends JpaRepository<DBSkill, Long> {
}
