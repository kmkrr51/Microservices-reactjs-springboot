package com.snowrepo.itsm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, UUID> {
  Optional<Problem> findByProblemNumber(String problemNumber);
}
