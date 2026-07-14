package com.snowrepo.cmdb.presentation;

import com.snowrepo.cmdb.application.ConfigurationItemService;
import com.snowrepo.cmdb.domain.ConfigurationItem;
import com.snowrepo.cmdb.domain.ConfigurationItem.CIStatus;
import com.snowrepo.cmdb.presentation.dto.ConfigurationItemResponse;
import com.snowrepo.cmdb.presentation.dto.CreateConfigurationItemRequest;
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
@RequestMapping("/api/v1/cis")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Configuration Items", description = "Configuration Management Database APIs")
public class ConfigurationItemController {

  private final ConfigurationItemService ciService;

  @PostMapping
  @Operation(summary = "Create a new configuration item")
  public ResponseEntity<ConfigurationItemResponse> createCI(
      @Valid @RequestBody CreateConfigurationItemRequest request
  ) {
    log.info("Creating configuration item: {}", request.getName());

    ConfigurationItem ci = ciService.createCI(
        request.getName(),
        request.getType(),
        request.getDescription(),
        request.getOwner(),
        "system"
    );

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ConfigurationItemResponse.fromEntity(ci));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get configuration item by ID")
  public ResponseEntity<ConfigurationItemResponse> getCI(@PathVariable UUID id) {
    log.info("Fetching configuration item: {}", id);

    ConfigurationItem ci = ciService.getCI(id);
    return ResponseEntity.ok(ConfigurationItemResponse.fromEntity(ci));
  }

  @GetMapping
  @Operation(summary = "Get all configuration items")
  public ResponseEntity<List<ConfigurationItemResponse>> getAllCIs() {
    log.info("Fetching all configuration items");

    List<ConfigurationItemResponse> cis = ciService.getAllCIs()
        .stream()
        .map(ConfigurationItemResponse::fromEntity)
        .toList();

    return ResponseEntity.ok(cis);
  }

  @GetMapping("/name/{name}")
  @Operation(summary = "Get configuration item by name")
  public ResponseEntity<ConfigurationItemResponse> getCIByName(
      @PathVariable String name
  ) {
    log.info("Fetching configuration item by name: {}", name);

    ConfigurationItem ci = ciService.getCIByName(name);
    return ResponseEntity.ok(ConfigurationItemResponse.fromEntity(ci));
  }

  @PutMapping("/{id}/status")
  @Operation(summary = "Update configuration item status")
  public ResponseEntity<ConfigurationItemResponse> changeCIStatus(
      @PathVariable UUID id,
      @RequestParam CIStatus status
  ) {
    log.info("Updating configuration item status: {} -> {}", id, status);

    ConfigurationItem ci = ciService.changeCIStatus(id, status, "system");
    return ResponseEntity.ok(ConfigurationItemResponse.fromEntity(ci));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update configuration item")
  public ResponseEntity<ConfigurationItemResponse> updateCI(
      @PathVariable UUID id,
      @Valid @RequestBody CreateConfigurationItemRequest request
  ) {
    log.info("Updating configuration item: {}", id);

    ConfigurationItem ci = ciService.updateCI(
        id,
        request.getName(),
        request.getDescription(),
        "system"
    );

    return ResponseEntity.ok(ConfigurationItemResponse.fromEntity(ci));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete configuration item")
  public ResponseEntity<Void> deleteCI(@PathVariable UUID id) {
    log.info("Deleting configuration item: {}", id);

    ciService.deleteCI(id);
    return ResponseEntity.noContent().build();
  }
}
