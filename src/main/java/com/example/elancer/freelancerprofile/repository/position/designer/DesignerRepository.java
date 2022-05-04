package com.example.elancer.freelancerprofile.repository.position.designer;

import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesignerRepository extends JpaRepository<Designer, Long> {
    Optional<Designer> findByFreelancerProfileNum(Long profileNum);
}
