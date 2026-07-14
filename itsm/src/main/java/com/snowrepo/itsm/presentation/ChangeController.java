package com.snowrepo.itsm.presentation;

import com.snowrepo.itsm.application.ChangeService;
import com.snowrepo.itsm.domain.Change;
import com.snowrepo.itsm.domain.Change.ChangeStatus;
import com.snowrepo.itsm.domain.Change.ChangeType;
import com.snowrepo.itsm.domain.Change.RiskLevel;
import com.snowrepo.itsm.presentation.dto.ChangeResponse;
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
@RequestMapping("/api/v1/changes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Changes", description = "Change Management APIs")
public class ChangeController {

  private final ChangeService changeService;

  @PostMapping
  @Operation(summary = "Create a new change")
  public ResponseEntity<ChangeResponse> createChange(@RequestBody Map<String, Object> body) {
    ChangeType type = body.get("type") != null
        ? ChangeType.valueOf((String) body.get("type")) : ChangeType.NORMAL;
    RiskLevel risk = body.get("riskLevel") != null
        ? RiskLevel.valueOf((String) body.get("riskLevel")) : RiskLevel.LOW;

    Change change = changeService.createChange(
        (String) body.getOrDefault("changeNumber", "CHG-" + System.currentTimeMillis()),
        (String) body.get("title"),
        (String) body.get("description"),
        type, risk, "system"
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(ChangeResponse.fromEntity(change));
  }

  @GetMapping
  @Operation(summary = "Get all changes")
  public ResponseEntity<List<ChangeResponse>> getAllChanges() {
    List<ChangeResponse> changes = changeService.getAllChanges()
        .stream().map(ChangeResponse::fromEntity).toList();
    return ResponseEntity.ok(changes);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get change by ID")
  public ResponseEntity<ChangeResponse> getChange(@PathVariable UUID id) {
    return ResponseEntity.ok(ChangeResponse.fromEntity(changeService.getChange(id)));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update change")
  public ResponseEntity<ChangeResponse> updateChange(
      @PathVariable UUID id, @RequestBody Map<String, Object> body) {
    Change change = changeService.updateChange(id,
        (String) body.get("title"),
        (String) body.get("description"),
        (String) body.get("impactAssessment"),
        (String) body.get("rollbackPlan"),
        (String) body.get("implementationSchedule"),
        "system");
    return ResponseEntity.ok(ChangeResponse.fromEntity(change));
  }

  @PutMapping("/{id}/status")
  @Operation(summary = "Update change status")
  public ResponseEntity<ChangeResponse> updateStatus(
      @PathVariable UUID id, @RequestParam ChangeStatus status) {
    return ResponseEntity.ok(ChangeResponse.fromEntity(
        changeService.updateStatus(id, status, "system")));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete change")
  public ResponseEntity<Void> deleteChange(@PathVariable UUID id) {
    changeService.deleteChange(id);
    return ResponseEntity.noContent().build();
  }
}
