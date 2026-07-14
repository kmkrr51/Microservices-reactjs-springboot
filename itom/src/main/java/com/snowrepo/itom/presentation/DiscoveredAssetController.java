package com.snowrepo.itom.presentation;

import com.snowrepo.itom.application.DiscoveredAssetService;
import com.snowrepo.itom.domain.DiscoveredAsset;
import com.snowrepo.itom.domain.DiscoveredAsset.AssetStatus;
import com.snowrepo.itom.presentation.dto.DiscoveredAssetResponse;
import com.snowrepo.itom.presentation.dto.CreateDiscoveredAssetRequest;
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
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Discovered Assets", description = "IT Operations Management APIs")
public class DiscoveredAssetController {

  private final DiscoveredAssetService assetService;

  @PostMapping
  @Operation(summary = "Create a new discovered asset")
  public ResponseEntity<DiscoveredAssetResponse> createAsset(
      @Valid @RequestBody CreateDiscoveredAssetRequest request
  ) {
    log.info("Creating discovered asset: {}", request.getName());

    DiscoveredAsset asset = assetService.createAsset(
        request.getName(),
        request.getAssetType(),
        request.getDescription(),
        request.getIpAddress(),
        request.getHostname(),
        "system"
    );

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(DiscoveredAssetResponse.fromEntity(asset));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get discovered asset by ID")
  public ResponseEntity<DiscoveredAssetResponse> getAsset(@PathVariable UUID id) {
    log.info("Fetching discovered asset: {}", id);

    DiscoveredAsset asset = assetService.getAsset(id);
    return ResponseEntity.ok(DiscoveredAssetResponse.fromEntity(asset));
  }

  @GetMapping
  @Operation(summary = "Get all discovered assets")
  public ResponseEntity<List<DiscoveredAssetResponse>> getAllAssets() {
    log.info("Fetching all discovered assets");

    List<DiscoveredAssetResponse> assets = assetService.getAllAssets()
        .stream()
        .map(DiscoveredAssetResponse::fromEntity)
        .toList();

    return ResponseEntity.ok(assets);
  }

  @GetMapping("/hostname/{hostname}")
  @Operation(summary = "Get discovered asset by hostname")
  public ResponseEntity<DiscoveredAssetResponse> getAssetByHostname(
      @PathVariable String hostname
  ) {
    log.info("Fetching discovered asset by hostname: {}", hostname);

    DiscoveredAsset asset = assetService.getAssetByHostname(hostname);
    return ResponseEntity.ok(DiscoveredAssetResponse.fromEntity(asset));
  }

  @GetMapping("/ip/{ipAddress}")
  @Operation(summary = "Get discovered asset by IP address")
  public ResponseEntity<DiscoveredAssetResponse> getAssetByIpAddress(
      @PathVariable String ipAddress
  ) {
    log.info("Fetching discovered asset by IP: {}", ipAddress);

    DiscoveredAsset asset = assetService.getAssetByIpAddress(ipAddress);
    return ResponseEntity.ok(DiscoveredAssetResponse.fromEntity(asset));
  }

  @PutMapping("/{id}/status")
  @Operation(summary = "Update discovered asset status")
  public ResponseEntity<DiscoveredAssetResponse> updateAssetStatus(
      @PathVariable UUID id,
      @RequestParam AssetStatus status
  ) {
    log.info("Updating discovered asset status: {} -> {}", id, status);

    DiscoveredAsset asset = assetService.updateAssetStatus(id, status, "system");
    return ResponseEntity.ok(DiscoveredAssetResponse.fromEntity(asset));
  }

  @PutMapping("/{id}/discover")
  @Operation(summary = "Record asset discovery")
  public ResponseEntity<DiscoveredAssetResponse> recordDiscovery(
      @PathVariable UUID id
  ) {
    log.info("Recording asset discovery: {}", id);

    DiscoveredAsset asset = assetService.recordAssetDiscovery(id, "system");
    return ResponseEntity.ok(DiscoveredAssetResponse.fromEntity(asset));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete discovered asset")
  public ResponseEntity<Void> deleteAsset(@PathVariable UUID id) {
    log.info("Deleting discovered asset: {}", id);

    assetService.deleteAsset(id);
    return ResponseEntity.noContent().build();
  }
}
