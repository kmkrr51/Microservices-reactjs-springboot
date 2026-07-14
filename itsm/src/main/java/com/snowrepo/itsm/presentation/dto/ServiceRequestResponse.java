package com.snowrepo.itsm.presentation.dto;

import com.snowrepo.itsm.domain.ServiceRequest;
import com.snowrepo.itsm.domain.ServiceRequest.RequestPriority;
import com.snowrepo.itsm.domain.ServiceRequest.RequestStatus;
import com.snowrepo.itsm.domain.ServiceRequest.RequestType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServiceRequestResponse {
  private UUID id;
  private String requestNumber;
  private String requestType;
  private String title;
  private String description;
  private RequestStatus status;
  private RequestPriority priority;
  private String requester;
  private String requestedService;
  private String assignedTo;
  private List<Object> tasks;
  private String fulfillmentDetails;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime fulfilledAt;
  private LocalDateTime closedAt;

  public static ServiceRequestResponse fromEntity(ServiceRequest r) {
    return ServiceRequestResponse.builder()
        .id(r.getId())
        .requestNumber(r.getRequestNumber())
        .requestType(r.getRequestType().name())
        .title(r.getTitle())
        .description(r.getDescription())
        .status(r.getStatus())
        .priority(r.getPriority())
        .requester(r.getRequester())
        .requestedService(r.getRequestedService())
        .assignedTo(r.getAssignedTo())
        .tasks(Collections.emptyList())
        .fulfillmentDetails(r.getFulfillmentDetails())
        .createdAt(r.getCreatedAt())
        .updatedAt(r.getUpdatedAt())
        .fulfilledAt(r.getFulfilledAt())
        .closedAt(r.getClosedAt())
        .build();
  }
}
