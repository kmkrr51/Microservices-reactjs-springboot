package com.snowrepo.itsm.domain;

import com.snowrepo.domain.AggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "changes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Change extends AggregateRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  @Column(name = "change_number", nullable = false, unique = true)
  private String changeNumber;

  @NotBlank
  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private ChangeType type;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ChangeStatus status;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "risk_level", nullable = false)
  private RiskLevel riskLevel;

  @Column(name = "impact_assessment", columnDefinition = "TEXT")
  private String impactAssessment;

  @Column(name = "rollback_plan", columnDefinition = "TEXT")
  private String rollbackPlan;

  @Column(name = "implementation_schedule")
  private String implementationSchedule;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "implemented_at")
  private LocalDateTime implementedAt;

  @Column(name = "version")
  private Long version;

  public static Change create(String changeNumber, String title, String description,
      ChangeType type, RiskLevel riskLevel, String createdBy) {
    return Change.builder()
        .id(UUID.randomUUID())
        .changeNumber(changeNumber)
        .title(title)
        .description(description)
        .type(type)
        .status(ChangeStatus.DRAFT)
        .riskLevel(riskLevel)
        .createdBy(createdBy)
        .updatedBy(createdBy)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .version(1L)
        .build();
  }

  public void updateStatus(ChangeStatus newStatus, String updatedBy) {
    this.status = newStatus;
    this.updatedBy = updatedBy;
    this.updatedAt = LocalDateTime.now();
    if (newStatus == ChangeStatus.IMPLEMENTED) {
      this.implementedAt = LocalDateTime.now();
    }
  }

  public enum ChangeStatus {
    DRAFT, SUBMITTED, APPROVED, REJECTED, IMPLEMENTED, CLOSED
  }

  public enum ChangeType {
    STANDARD, EMERGENCY, NORMAL
  }

  public enum RiskLevel {
    HIGH, MEDIUM, LOW
  }
}
