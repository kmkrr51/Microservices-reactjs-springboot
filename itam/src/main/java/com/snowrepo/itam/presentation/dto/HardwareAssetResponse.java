package com.snowrepo.itam.presentation.dto;

import com.snowrepo.itam.domain.HardwareAsset;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HardwareAssetResponse {

  private UUID id;
  private String assetTag;
  private String name;
  private String assetType;
  private String description;
  private String status;
  private String manufacturer;
  private String model;
  private String serialNumber;
  private LocalDate purchaseDate;
  private BigDecimal purchaseCost;
  private LocalDate warrantyExpiryDate;
  private String assignedTo;
  private String location;
  private String costCenter;
  private String createdBy;
  private LocalDateTime createdAt;
  private String updatedBy;
  private LocalDateTime updatedAt;
  private Long version;

  public static HardwareAssetResponse fromEntity(HardwareAsset asset) {
    return HardwareAssetResponse.builder()
        .id(asset.getId())
        .assetTag(asset.getAssetTag())
        .name(asset.getName())
        .assetType(asset.getAssetType())
        .description(asset.getDescription())
        .status(asset.getStatus().toString())
        .manufacturer(asset.getManufacturer())
        .model(asset.getModel())
        .serialNumber(asset.getSerialNumber())
        .purchaseDate(asset.getPurchaseDate())
        .purchaseCost(asset.getPurchaseCost())
        .warrantyExpiryDate(asset.getWarrantyExpiryDate())
        .assignedTo(asset.getAssignedTo())
        .location(asset.getLocation())
        .costCenter(asset.getCostCenter())
        .createdBy(asset.getCreatedBy())
        .createdAt(asset.getCreatedAt())
        .updatedBy(asset.getUpdatedBy())
        .updatedAt(asset.getUpdatedAt())
        .version(asset.getVersion())
        .build();
  }
}
