package com.snowrepo.itsm.presentation.dto;

import com.snowrepo.itsm.domain.Change;
import com.snowrepo.itsm.domain.Change.ChangeStatus;
import com.snowrepo.itsm.domain.Change.ChangeType;
import com.snowrepo.itsm.domain.Change.RiskLevel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChangeResponse {
  private UUID id;
  private String changeNumber;
  private String title;
  private String description;
  private ChangeType type;
  private ChangeStatus status;
  private RiskLevel riskLevel;
  private String impactAssessment;
  private String rollbackPlan;
  private String implementationSchedule;
  private String createdBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime implementedAt;

  public static ChangeResponse fromEntity(Change c) {
    return ChangeResponse.builder()
        .id(c.getId())
        .changeNumber(c.getChangeNumber())
        .title(c.getTitle())
        .description(c.getDescription())
        .type(c.getType())
        .status(c.getStatus())
        .riskLevel(c.getRiskLevel())
        .impactAssessment(c.getImpactAssessment())
        .rollbackPlan(c.getRollbackPlan())
        .implementationSchedule(c.getImplementationSchedule())
        .createdBy(c.getCreatedBy())
        .createdAt(c.getCreatedAt())
        .updatedAt(c.getUpdatedAt())
        .implementedAt(c.getImplementedAt())
        .build();
  }
}
