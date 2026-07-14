package com.snowrepo.cmdb.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateConfigurationItemRequest {

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Type is required")
  private String type;

  private String description;

  private String owner;
}
