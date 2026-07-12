package com.snowrepo.itam.domain;

import com.snowrepo.domain.AggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hardware_assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HardwareAsset extends AggregateRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @NotBlank(message = "Asset tag is required")
  @Column(name = "asset_tag", nullable = false, unique = true)
  private String assetTag;

  @NotBlank(message = "Asset name is required")
  @Column(name = "name", nullable = false)
  private String name;

  @NotBlank(message = "Asset type is required")
  @Column(name = "asset_type", nullable = false)
  private String assetType;

  @Column(name = "description")
  private String description;

  @NotNull(message = "Status is required")
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private AssetStatus status;

  @Column(name = "manufacturer")
  private String manufacturer;

  @Column(name = "model")
  private String model;

  @Column(name = "serial_number")
  private String serialNumber;

  @Column(name = "purchase_date")
  private LocalDate purchaseDate;

  @Column(name = "purchase_cost", precision = 19, scale = 2)
  private BigDecimal purchaseCost;

  @Column(name = "warranty_expiry_date")
  private LocalDate warrantyExpiryDate;

  @Column(name = "assigned_to")
  private String assignedTo;

  @Column(name = "location")
  private String location;

  @Column(name = "cost_center")
  private String costCenter;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "version")
  private Long version;

  public static HardwareAsset create(
      String assetTag,
      String name,
      String assetType,
      String manufacturer,
      String model,
      String serialNumber,
      BigDecimal purchaseCost,
      String createdBy
  ) {
    HardwareAsset asset = HardwareAsset.builder()
        .id(UUID.randomUUID())
        .assetTag(assetTag)
        .name(name)
        .assetType(assetType)
        .status(AssetStatus.ACTIVE)
        .manufacturer(manufacturer)
        .model(model)
        .serialNumber(serialNumber)
        .purchaseCost(purchaseCost)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .createdBy(createdBy)
        .updatedBy(createdBy)
        .version(1L)
        .build();

    asset.addDomainEvent(new HardwareAssetCreatedEvent(
        asset.getId(),
        asset.getAssetTag(),
        asset.getName(),
        asset.getAssetType(),
        asset.getStatus()
    ));

    return asset;
  }

  public void updateStatus(AssetStatus newStatus, String updatedBy) {
    if (!Objects.equals(this.status, newStatus)) {
      this.status = newStatus;
      this.updatedAt = LocalDateTime.now();
      this.updatedBy = updatedBy;
      this.version = this.version + 1;

      addDomainEvent(new HardwareAssetStatusChangedEvent(
          this.getId(),
          this.getAssetTag(),
          newStatus
      ));
    }
  }

  public void assignTo(String assignee, String updatedBy) {
    if (!Objects.equals(this.assignedTo, assignee)) {
      this.assignedTo = assignee;
      this.updatedAt = LocalDateTime.now();
      this.updatedBy = updatedBy;
      this.version = this.version + 1;

      addDomainEvent(new HardwareAssetAssignedEvent(
          this.getId(),
          this.getAssetTag(),
          assignee
      ));
    }
  }

  public enum AssetStatus {
    ACTIVE,
    INACTIVE,
    RETIRED,
    DISPOSED,
    LOST;
  }
}
