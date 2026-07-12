package com.snowrepo.itsm.domain;

import com.snowrepo.domain.AggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "incidents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident extends AggregateRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @NotBlank(message = "Incident number is required")
  @Column(name = "incident_number", nullable = false, unique = true)
  private String incidentNumber;

  @NotBlank(message = "Title is required")
  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  @NotNull(message = "Status is required")
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private IncidentStatus status;

  @NotNull(message = "Priority is required")
  @Enumerated(EnumType.STRING)
  @Column(name = "priority", nullable = false)
  private Priority priority;

  @Column(name = "assigned_to")
  private String assignedTo;

  @Column(name = "reporter")
  private String reporter;

  @Column(name = "affected_service")
  private String affectedService;

  @Column(name = "impact_level")
  private String impactLevel;

  @Column(name = "urgency_level")
  private String urgencyLevel;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "resolved_at")
  private LocalDateTime resolvedAt;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "version")
  private Long version;

  public static Incident create(
      String incidentNumber,
      String title,
      String description,
      Priority priority,
      String reporter,
      String createdBy
  ) {
    Incident incident = Incident.builder()
        .id(UUID.randomUUID())
        .incidentNumber(incidentNumber)
        .title(title)
        .description(description)
        .status(IncidentStatus.NEW)
        .priority(priority)
        .reporter(reporter)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .createdBy(createdBy)
        .updatedBy(createdBy)
        .version(1L)
        .build();

    incident.recordDomainEvent(new IncidentCreatedEvent(
        incident.getId(),
        incident.getIncidentNumber(),
        incident.getTitle(),
        incident.getPriority(),
        incident.getStatus()
    ));

    return incident;
  }

  public void updateStatus(IncidentStatus newStatus, String updatedBy) {
    if (!Objects.equals(this.status, newStatus)) {
      this.status = newStatus;
      this.updatedAt = LocalDateTime.now();
      this.updatedBy = updatedBy;
      this.version = this.version + 1;

      if (newStatus == IncidentStatus.RESOLVED) {
        this.resolvedAt = LocalDateTime.now();
      }

      recordDomainEvent(new IncidentStatusChangedEvent(
          this.getId(),
          this.getIncidentNumber(),
          newStatus
      ));
    }
  }

  public void assignTo(String assignee, String updatedBy) {
    if (!Objects.equals(this.assignedTo, assignee)) {
      this.assignedTo = assignee;
      this.updatedAt = LocalDateTime.now();
      this.updatedBy = updatedBy;
      this.version = this.version + 1;

      recordDomainEvent(new IncidentAssignedEvent(
          this.getId(),
          this.getIncidentNumber(),
          assignee
      ));
    }
  }

  public enum IncidentStatus {
    NEW,
    ASSIGNED,
    IN_PROGRESS,
    PENDING,
    RESOLVED,
    CLOSED,
    REOPENED;
  }

  public enum Priority {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW;
  }
}
