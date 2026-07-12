package com.snowrepo.cmdb.domain;

import com.snowrepo.domain.AggregateRoot;
import com.snowrepo.domain.DomainEvent;
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
@Table(name = "configuration_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationItem extends AggregateRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;


  @NotBlank(message = "CI name is required")
  @Column(name = "name", nullable = false)
  private String name;

  @NotBlank(message = "CI type is required")
  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "description")
  private String description;

  @NotNull(message = "CI status is required")
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private CIStatus status;

  @Column(name = "owner")
  private String owner;

  @Column(name = "location")
  private String location;

  @Column(name = "cost_center")
  private String costCenter;

  @Column(name = "business_service")
  private String businessService;

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

  public static ConfigurationItem create(
      String name,
      String type,
      String description,
      String owner,
      String createdBy
  ) {
    ConfigurationItem ci = ConfigurationItem.builder()
        .id(UUID.randomUUID())
        .name(name)
        .type(type)
        .description(description)
        .status(CIStatus.ACTIVE)
        .owner(owner)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .createdBy(createdBy)
        .updatedBy(createdBy)
        .version(1L)
        .build();

    ci.addDomainEvent(new ConfigurationItemCreatedEvent(
        ci.getId(),
        ci.getName(),
        ci.getType(),
        ci.getStatus()
    ));

    return ci;
  }

  public void update(String name, String description, String updatedBy) {
    if (!Objects.equals(this.name, name) || !Objects.equals(this.description, description)) {
      this.name = name;
      this.description = description;
      this.updatedAt = LocalDateTime.now();
      this.updatedBy = updatedBy;
      this.version = this.version + 1;

      addDomainEvent(new ConfigurationItemUpdatedEvent(
          this.getId(),
          this.getName(),
          this.getType(),
          this.getStatus()
      ));
    }
  }

  public void changeStatus(CIStatus newStatus, String updatedBy) {
    if (!Objects.equals(this.status, newStatus)) {
      this.status = newStatus;
      this.updatedAt = LocalDateTime.now();
      this.updatedBy = updatedBy;
      this.version = this.version + 1;

      addDomainEvent(new ConfigurationItemStatusChangedEvent(
          this.getId(),
          this.getName(),
          this.getType(),
          newStatus
      ));
    }
  }

  public enum CIStatus {
    ACTIVE,
    INACTIVE,
    RETIRED,
    PENDING,
    ARCHIVED;
  }
}
