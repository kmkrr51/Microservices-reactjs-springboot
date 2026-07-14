package com.snowrepo.itam.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHardwareAssetRequest {

  @NotBlank(message = "Asset tag is required")
  private String assetTag;

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Asset type is required")
  private String assetType;

  private String manufacturer;

  private String model;

  private String serialNumber;

  private BigDecimal purchaseCost;
}
