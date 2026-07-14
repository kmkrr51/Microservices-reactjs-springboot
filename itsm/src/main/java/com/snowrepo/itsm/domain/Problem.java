package com.snowrepo.itsm.domain;

import com.snowrepo.domain.AggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "problems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem extends AggregateRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  @Column(name = "problem_number", nullable = false, unique = true)
  private String problemNumber;

  @NotBlank
  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ProblemStatus status;

  @Column(name = "root_cause", columnDefinition = "TEXT")
  private String rootCause;

  @Column(name = "impacted_services")
  private String impactedServices;

  @Column(name = "related_incidents")
  private String relatedIncidents;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "resolved_at")
  private LocalDateTime resolvedAt;

  @Column(name = "version")
  private Long version;

  public static Problem create(String problemNumber, String title, String description, String createdBy) {
    return Problem.builder()
        .id(UUID.randomUUID())
        .problemNumber(problemNumber)
        .title(title)
        .description(description)
        .status(ProblemStatus.IDENTIFIED)
        .createdBy(createdBy)
        .updatedBy(createdBy)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .version(1L)
        .build();
  }

  public void updateStatus(ProblemStatus newStatus, String updatedBy) {
    this.status = newStatus;
    this.updatedBy = updatedBy;
    this.updatedAt = LocalDateTime.now();
    if (newStatus == ProblemStatus.RESOLVED || newStatus == ProblemStatus.CLOSED) {
      this.resolvedAt = LocalDateTime.now();
    }
  }

  public enum ProblemStatus {
    IDENTIFIED, INVESTIGATING, RESOLVED, CLOSED
  }
}
