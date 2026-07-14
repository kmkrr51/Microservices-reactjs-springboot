package com.snowrepo.itsm.presentation.dto;

import com.snowrepo.itsm.domain.Problem;
import com.snowrepo.itsm.domain.Problem.ProblemStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProblemResponse {
  private UUID id;
  private String problemNumber;
  private String title;
  private String description;
  private ProblemStatus status;
  private String rootCause;
  private List<String> relatedIncidents;
  private List<String> impactedServices;
  private String createdBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime resolvedAt;

  public static ProblemResponse fromEntity(Problem p) {
    return ProblemResponse.builder()
        .id(p.getId())
        .problemNumber(p.getProblemNumber())
        .title(p.getTitle())
        .description(p.getDescription())
        .status(p.getStatus())
        .rootCause(p.getRootCause())
        .relatedIncidents(splitCsv(p.getRelatedIncidents()))
        .impactedServices(splitCsv(p.getImpactedServices()))
        .createdBy(p.getCreatedBy())
        .createdAt(p.getCreatedAt())
        .updatedAt(p.getUpdatedAt())
        .resolvedAt(p.getResolvedAt())
        .build();
  }

  private static List<String> splitCsv(String csv) {
    if (csv == null || csv.isBlank()) return Collections.emptyList();
    return Arrays.asList(csv.split(","));
  }
}
