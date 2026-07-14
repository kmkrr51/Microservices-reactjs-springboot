package com.snowrepo.itsm.presentation;

import com.snowrepo.itsm.application.IncidentService;
import com.snowrepo.itsm.domain.Incident;
import com.snowrepo.itsm.domain.Incident.IncidentStatus;
import com.snowrepo.itsm.presentation.dto.CreateIncidentRequest;
import com.snowrepo.itsm.presentation.dto.IncidentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/incidents")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Incidents", description = "Incident Management APIs")
public class IncidentController {

  private final IncidentService incidentService;

  @PostMapping
  @Operation(summary = "Create a new incident")
  public ResponseEntity<IncidentResponse> createIncident(
      @Valid @RequestBody CreateIncidentRequest request
  ) {
    String incidentNumber = (request.getIncidentNumber() != null && !request.getIncidentNumber().isBlank())
        ? request.getIncidentNumber()
        : "INC-" + System.currentTimeMillis();
    String reporter = (request.getReporter() != null && !request.getReporter().isBlank())
        ? request.getReporter()
        : (request.getCreatedBy() != null ? request.getCreatedBy() : "system");

    log.info("Creating incident: {}", incidentNumber);

    Incident incident = incidentService.createIncident(
        incidentNumber,
        request.getTitle(),
        request.getDescription(),
        request.getPriority(),
        reporter,
        "system"
    );

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(IncidentResponse.fromEntity(incident));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get incident by ID")
  public ResponseEntity<IncidentResponse> getIncident(@PathVariable UUID id) {
    log.info("Fetching incident: {}", id);

    Incident incident = incidentService.getIncident(id);
    return ResponseEntity.ok(IncidentResponse.fromEntity(incident));
  }

  @GetMapping
  @Operation(summary = "Get all incidents")
  public ResponseEntity<List<IncidentResponse>> getAllIncidents() {
    log.info("Fetching all incidents");

    List<IncidentResponse> incidents = incidentService.getAllIncidents()
        .stream()
        .map(IncidentResponse::fromEntity)
        .toList();

    return ResponseEntity.ok(incidents);
  }

  @GetMapping("/number/{incidentNumber}")
  @Operation(summary = "Get incident by number")
  public ResponseEntity<IncidentResponse> getIncidentByNumber(
      @PathVariable String incidentNumber
  ) {
    log.info("Fetching incident by number: {}", incidentNumber);

    Incident incident = incidentService.getIncidentByNumber(incidentNumber);
    return ResponseEntity.ok(IncidentResponse.fromEntity(incident));
  }

  @PutMapping("/{id}/status")
  @Operation(summary = "Update incident status")
  public ResponseEntity<IncidentResponse> updateIncidentStatus(
      @PathVariable UUID id,
      @RequestParam IncidentStatus status
  ) {
    log.info("Updating incident status: {} -> {}", id, status);

    Incident incident = incidentService.updateIncidentStatus(id, status, "system");
    return ResponseEntity.ok(IncidentResponse.fromEntity(incident));
  }

  @PutMapping("/{id}/assign")
  @Operation(summary = "Assign incident to a user")
  public ResponseEntity<IncidentResponse> assignIncident(
      @PathVariable UUID id,
      @RequestParam String assignee
  ) {
    log.info("Assigning incident {} to {}", id, assignee);

    Incident incident = incidentService.assignIncident(id, assignee, "system");
    return ResponseEntity.ok(IncidentResponse.fromEntity(incident));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete incident")
  public ResponseEntity<Void> deleteIncident(@PathVariable UUID id) {
    log.info("Deleting incident: {}", id);

    incidentService.deleteIncident(id);
    return ResponseEntity.noContent().build();
  }
}
