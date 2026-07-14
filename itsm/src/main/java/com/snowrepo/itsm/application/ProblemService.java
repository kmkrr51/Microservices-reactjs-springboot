package com.snowrepo.itsm.application;

import com.snowrepo.itsm.domain.Problem;
import com.snowrepo.itsm.domain.Problem.ProblemStatus;
import com.snowrepo.itsm.domain.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProblemService {

  private final ProblemRepository problemRepository;

  public Problem createProblem(String problemNumber, String title, String description, String createdBy) {
    log.info("Creating problem: {}", problemNumber);
    Problem problem = Problem.create(problemNumber, title, description, createdBy);
    return problemRepository.save(problem);
  }

  public Problem getProblem(UUID id) {
    return problemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Problem not found: " + id));
  }

  public List<Problem> getAllProblems() {
    return problemRepository.findAll();
  }

  public Problem updateStatus(UUID id, ProblemStatus status, String updatedBy) {
    Problem problem = getProblem(id);
    problem.updateStatus(status, updatedBy);
    return problemRepository.save(problem);
  }

  public Problem updateProblem(UUID id, String title, String description, String rootCause,
      String impactedServices, String updatedBy) {
    Problem problem = getProblem(id);
    if (title != null) problem.setTitle(title);
    if (description != null) problem.setDescription(description);
    if (rootCause != null) problem.setRootCause(rootCause);
    if (impactedServices != null) problem.setImpactedServices(impactedServices);
    problem.setUpdatedBy(updatedBy);
    problem.setUpdatedAt(java.time.LocalDateTime.now());
    return problemRepository.save(problem);
  }

  public void deleteProblem(UUID id) {
    problemRepository.deleteById(id);
  }
}
