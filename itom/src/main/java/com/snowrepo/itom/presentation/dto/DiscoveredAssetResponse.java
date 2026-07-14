package com.snowrepo.itom.presentation.dto;

import com.snowrepo.itom.domain.DiscoveredAsset;
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
public class DiscoveredAssetResponse {

  private UUID id;
  private String name;
  private String assetType;
  private String description;
  private String status;
  private String ipAddress;
  private String hostname;
  private String osType;
  private String osVersion;
  private String location;
  private String owner;
  private LocalDateTime lastDiscoveredAt;
  private String createdBy;
  private LocalDateTime createdAt;
  private String updatedBy;
  private LocalDateTime updatedAt;
  private Long version;

  public static DiscoveredAssetResponse fromEntity(DiscoveredAsset asset) {
    return DiscoveredAssetResponse.builder()
        .id(asset.getId())
        .name(asset.getName())
        .assetType(asset.getAssetType())
        .description(asset.getDescription())
        .status(asset.getStatus().toString())
        .ipAddress(asset.getIpAddress())
        .hostname(asset.getHostname())
        .osType(asset.getOsType())
        .osVersion(asset.getOsVersion())
        .location(asset.getLocation())
        .owner(asset.getOwner())
        .lastDiscoveredAt(asset.getLastDiscoveredAt())
        .createdBy(asset.getCreatedBy())
        .createdAt(asset.getCreatedAt())
        .updatedBy(asset.getUpdatedBy())
        .updatedAt(asset.getUpdatedAt())
        .version(asset.getVersion())
        .build();
  }
}
