package com.example.elancer.wishprojects.repository;

import com.example.elancer.wishprojects.model.WishProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishProjectRepository extends JpaRepository<WishProject, Long> {
    List<WishProject> findByFreelancerNum(Long freelancerNum);

    Optional<WishProject> findByFreelancerNumAndProjectNum(Long freelancerNum, Long projectNum);
}
