package com.snowrepo.itsm.presentation.dto;

import com.snowrepo.itsm.domain.Incident;
import com.snowrepo.itsm.domain.Incident.IncidentStatus;
import com.snowrepo.itsm.domain.Incident.Priority;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentResponse {

  private UUID id;
  private String incidentNumber;
  private String title;
  private String description;
  private IncidentStatus status;
  private Priority priority;
  private String assignedTo;
  private String reporter;
  private String affectedService;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime resolvedAt;

  public static IncidentResponse fromEntity(Incident incident) {
    return IncidentResponse.builder()
        .id(incident.getId())
        .incidentNumber(incident.getIncidentNumber())
        .title(incident.getTitle())
        .description(incident.getDescription())
        .status(incident.getStatus())
        .priority(incident.getPriority())
        .assignedTo(incident.getAssignedTo())
        .reporter(incident.getReporter())
        .affectedService(incident.getAffectedService())
        .createdAt(incident.getCreatedAt())
        .updatedAt(incident.getUpdatedAt())
        .resolvedAt(incident.getResolvedAt())
        .build();
  }
}
