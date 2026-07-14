package com.snowrepo.itsm.presentation;

import com.snowrepo.itsm.application.ServiceRequestService;
import com.snowrepo.itsm.domain.ServiceRequest;
import com.snowrepo.itsm.domain.ServiceRequest.RequestPriority;
import com.snowrepo.itsm.domain.ServiceRequest.RequestStatus;
import com.snowrepo.itsm.domain.ServiceRequest.RequestType;
import com.snowrepo.itsm.presentation.dto.ServiceRequestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Requests", description = "Service Request Management APIs")
public class ServiceRequestController {

  private final ServiceRequestService serviceRequestService;

  @PostMapping
  @Operation(summary = "Create a new service request")
  public ResponseEntity<ServiceRequestResponse> createRequest(@RequestBody Map<String, Object> body) {
    String typeStr = (String) body.getOrDefault("requestType", "ROUTINE");
    RequestType type;
    try { type = RequestType.valueOf(typeStr); } catch (Exception e) { type = RequestType.ROUTINE; }

    String priorityStr = (String) body.getOrDefault("priority", "MEDIUM");
    RequestPriority priority;
    try { priority = RequestPriority.valueOf(priorityStr); } catch (Exception e) { priority = RequestPriority.MEDIUM; }

    ServiceRequest request = serviceRequestService.createRequest(
        (String) body.getOrDefault("requestNumber", "REQ-" + System.currentTimeMillis()),
        (String) body.get("title"),
        (String) body.get("description"),
        type, priority,
        (String) body.getOrDefault("requester", "system"),
        (String) body.get("requestedService"),
        "system"
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(ServiceRequestResponse.fromEntity(request));
  }

  @GetMapping
  @Operation(summary = "Get all service requests")
  public ResponseEntity<List<ServiceRequestResponse>> getAllRequests() {
    List<ServiceRequestResponse> requests = serviceRequestService.getAllRequests()
        .stream().map(ServiceRequestResponse::fromEntity).toList();
    return ResponseEntity.ok(requests);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get service request by ID")
  public ResponseEntity<ServiceRequestResponse> getRequest(@PathVariable UUID id) {
    return ResponseEntity.ok(ServiceRequestResponse.fromEntity(serviceRequestService.getRequest(id)));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update service request")
  public ResponseEntity<ServiceRequestResponse> updateRequest(
      @PathVariable UUID id, @RequestBody Map<String, Object> body) {
    ServiceRequest request = serviceRequestService.updateRequest(id,
        (String) body.get("title"),
        (String) body.get("description"),
        (String) body.get("assignedTo"),
        (String) body.get("fulfillmentDetails"),
        "system");
    return ResponseEntity.ok(ServiceRequestResponse.fromEntity(request));
  }

  @PutMapping("/{id}/status")
  @Operation(summary = "Update request status")
  public ResponseEntity<ServiceRequestResponse> updateStatus(
      @PathVariable UUID id, @RequestParam RequestStatus status) {
    return ResponseEntity.ok(ServiceRequestResponse.fromEntity(
        serviceRequestService.updateStatus(id, status, "system")));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete service request")
  public ResponseEntity<Void> deleteRequest(@PathVariable UUID id) {
    serviceRequestService.deleteRequest(id);
    return ResponseEntity.noContent().build();
  }
}
