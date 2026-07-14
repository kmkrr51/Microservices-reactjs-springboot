package com.snowrepo.itsm.presentation;

import com.snowrepo.itsm.application.ProblemService;
import com.snowrepo.itsm.domain.Problem;
import com.snowrepo.itsm.domain.Problem.ProblemStatus;
import com.snowrepo.itsm.presentation.dto.ProblemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Problems", description = "Problem Management APIs")
public class ProblemController {

  private final ProblemService problemService;

  @PostMapping
  @Operation(summary = "Create a new problem")
  public ResponseEntity<ProblemResponse> createProblem(@RequestBody Map<String, Object> body) {
    Problem problem = problemService.createProblem(
        (String) body.getOrDefault("problemNumber", "PRB-" + System.currentTimeMillis()),
        (String) body.get("title"),
        (String) body.get("description"),
        "system"
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(ProblemResponse.fromEntity(problem));
  }

  @GetMapping
  @Operation(summary = "Get all problems")
  public ResponseEntity<List<ProblemResponse>> getAllProblems() {
    List<ProblemResponse> problems = problemService.getAllProblems()
        .stream().map(ProblemResponse::fromEntity).toList();
    return ResponseEntity.ok(problems);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get problem by ID")
  public ResponseEntity<ProblemResponse> getProblem(@PathVariable UUID id) {
    return ResponseEntity.ok(ProblemResponse.fromEntity(problemService.getProblem(id)));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update problem")
  public ResponseEntity<ProblemResponse> updateProblem(
      @PathVariable UUID id, @RequestBody Map<String, Object> body) {
    Problem problem = problemService.updateProblem(id,
        (String) body.get("title"),
        (String) body.get("description"),
        (String) body.get("rootCause"),
        (String) body.get("impactedServices"),
        "system");
    return ResponseEntity.ok(ProblemResponse.fromEntity(problem));
  }

  @PutMapping("/{id}/status")
  @Operation(summary = "Update problem status")
  public ResponseEntity<ProblemResponse> updateStatus(
      @PathVariable UUID id, @RequestParam ProblemStatus status) {
    return ResponseEntity.ok(ProblemResponse.fromEntity(
        problemService.updateStatus(id, status, "system")));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete problem")
  public ResponseEntity<Void> deleteProblem(@PathVariable UUID id) {
    problemService.deleteProblem(id);
    return ResponseEntity.noContent().build();
  }
}
