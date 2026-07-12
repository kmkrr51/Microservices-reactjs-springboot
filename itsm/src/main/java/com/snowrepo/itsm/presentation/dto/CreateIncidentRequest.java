package com.snowrepo.itsm.presentation.dto;

import com.snowrepo.itsm.domain.Incident.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateIncidentRequest {

  @NotBlank(message = "Incident number is required")
  private String incidentNumber;

  @NotBlank(message = "Title is required")
  private String title;

  private String description;

  @NotNull(message = "Priority is required")
  private Priority priority;

  @NotBlank(message = "Reporter is required")
  private String reporter;
}
