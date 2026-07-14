package com.snowrepo.itom.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDiscoveredAssetRequest {

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Asset type is required")
  private String assetType;

  private String description;

  private String ipAddress;

  private String hostname;
}
