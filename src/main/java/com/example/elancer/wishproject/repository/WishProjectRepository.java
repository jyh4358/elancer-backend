package com.example.elancer.wishproject.repository;

import com.example.elancer.wishproject.model.WishProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishProjectRepository extends JpaRepository<WishProject, Long> {
}
