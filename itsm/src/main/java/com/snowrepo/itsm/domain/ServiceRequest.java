package com.snowrepo.itsm.domain;

import com.snowrepo.domain.AggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "service_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRequest extends AggregateRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  @Column(name = "request_number", nullable = false, unique = true)
  private String requestNumber;

  @NotBlank
  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "request_type", nullable = false)
  private RequestType requestType;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private RequestStatus status;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "priority", nullable = false)
  private RequestPriority priority;

  @NotBlank
  @Column(name = "requester", nullable = false)
  private String requester;

  @Column(name = "requested_service")
  private String requestedService;

  @Column(name = "assigned_to")
  private String assignedTo;

  @Column(name = "fulfillment_details", columnDefinition = "TEXT")
  private String fulfillmentDetails;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "fulfilled_at")
  private LocalDateTime fulfilledAt;

  @Column(name = "closed_at")
  private LocalDateTime closedAt;

  @Column(name = "version")
  private Long version;

  public static ServiceRequest create(String requestNumber, String title, String description,
      RequestType requestType, RequestPriority priority, String requester, String requestedService,
      String createdBy) {
    return ServiceRequest.builder()
        .id(UUID.randomUUID())
        .requestNumber(requestNumber)
        .title(title)
        .description(description)
        .requestType(requestType)
        .status(RequestStatus.NEW)
        .priority(priority)
        .requester(requester)
        .requestedService(requestedService)
        .createdBy(createdBy)
        .updatedBy(createdBy)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .version(1L)
        .build();
  }

  public void updateStatus(RequestStatus newStatus, String updatedBy) {
    this.status = newStatus;
    this.updatedBy = updatedBy;
    this.updatedAt = LocalDateTime.now();
    if (newStatus == RequestStatus.FULFILLED) {
      this.fulfilledAt = LocalDateTime.now();
    } else if (newStatus == RequestStatus.CLOSED) {
      this.closedAt = LocalDateTime.now();
    }
  }

  public enum RequestStatus {
    NEW, ASSIGNED, IN_PROGRESS, FULFILLED, CLOSED
  }

  public enum RequestType {
    STANDARD, URGENT, ROUTINE
  }

  public enum RequestPriority {
    CRITICAL, HIGH, MEDIUM, LOW
  }
}
