package com.snowrepo.cmdb.presentation.dto;

import com.snowrepo.cmdb.domain.ConfigurationItem;
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
public class ConfigurationItemResponse {

  private UUID id;
  private String name;
  private String type;
  private String description;
  private String status;
  private String owner;
  private String location;
  private String businessService;
  private String costCenter;
  private String createdBy;
  private LocalDateTime createdAt;
  private String updatedBy;
  private LocalDateTime updatedAt;
  private Long version;

  public static ConfigurationItemResponse fromEntity(ConfigurationItem ci) {
    return ConfigurationItemResponse.builder()
        .id(ci.getId())
        .name(ci.getName())
        .type(ci.getType())
        .description(ci.getDescription())
        .status(ci.getStatus().toString())
        .owner(ci.getOwner())
        .location(ci.getLocation())
        .businessService(ci.getBusinessService())
        .costCenter(ci.getCostCenter())
        .createdBy(ci.getCreatedBy())
        .createdAt(ci.getCreatedAt())
        .updatedBy(ci.getUpdatedBy())
        .updatedAt(ci.getUpdatedAt())
        .version(ci.getVersion())
        .build();
  }
}
