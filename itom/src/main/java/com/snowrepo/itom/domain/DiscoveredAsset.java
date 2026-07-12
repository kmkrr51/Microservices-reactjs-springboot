package com.snowrepo.itom.domain;

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
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discovered_assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscoveredAsset extends AggregateRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

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

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "hostname")
  private String hostname;

  @Column(name = "os_type")
  private String osType;

  @Column(name = "os_version")
  private String osVersion;

  @Column(name = "location")
  private String location;

  @Column(name = "owner")
  private String owner;

  @Column(name = "last_discovered_at")
  private LocalDateTime lastDiscoveredAt;

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

  public static DiscoveredAsset create(
      String name,
      String assetType,
      String description,
      String ipAddress,
      String hostname,
      String createdBy
  ) {
    DiscoveredAsset asset = DiscoveredAsset.builder()
        .id(UUID.randomUUID())
        .name(name)
        .assetType(assetType)
        .description(description)
        .status(AssetStatus.DISCOVERED)
        .ipAddress(ipAddress)
        .hostname(hostname)
        .lastDiscoveredAt(LocalDateTime.now())
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .createdBy(createdBy)
        .updatedBy(createdBy)
        .version(1L)
        .build();

    asset.recordDomainEvent(new AssetDiscoveredEvent(
        asset.getId(),
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

      recordDomainEvent(new AssetStatusChangedEvent(
          this.getId(),
          this.getName(),
          this.getAssetType(),
          newStatus
      ));
    }
  }

  public void recordDiscovery(String updatedBy) {
    this.lastDiscoveredAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.updatedBy = updatedBy;
    this.version = this.version + 1;

    recordDomainEvent(new AssetRediscoveredEvent(
        this.getId(),
        this.getName(),
        this.getAssetType()
    ));
  }

  public enum AssetStatus {
    DISCOVERED,
    MONITORED,
    DECOMMISSIONED,
    UNKNOWN;
  }
}
