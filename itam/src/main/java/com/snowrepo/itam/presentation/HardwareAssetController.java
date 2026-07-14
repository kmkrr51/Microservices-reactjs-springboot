package com.snowrepo.itam.presentation;

import com.snowrepo.itam.application.HardwareAssetService;
import com.snowrepo.itam.domain.HardwareAsset;
import com.snowrepo.itam.domain.HardwareAsset.AssetStatus;
import com.snowrepo.itam.presentation.dto.HardwareAssetResponse;
import com.snowrepo.itam.presentation.dto.CreateHardwareAssetRequest;
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
@Tag(name = "Hardware Assets", description = "IT Asset Management APIs")
public class HardwareAssetController {

  private final HardwareAssetService assetService;

  @PostMapping
  @Operation(summary = "Create a new hardware asset")
  public ResponseEntity<HardwareAssetResponse> createAsset(
      @Valid @RequestBody CreateHardwareAssetRequest request
  ) {
    log.info("Creating hardware asset: {}", request.getAssetTag());

    HardwareAsset asset = assetService.createAsset(
        request.getAssetTag(),
        request.getName(),
        request.getAssetType(),
        request.getManufacturer(),
        request.getModel(),
        request.getSerialNumber(),
        request.getPurchaseCost(),
        "system"
    );

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(HardwareAssetResponse.fromEntity(asset));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get hardware asset by ID")
  public ResponseEntity<HardwareAssetResponse> getAsset(@PathVariable UUID id) {
    log.info("Fetching hardware asset: {}", id);

    HardwareAsset asset = assetService.getAsset(id);
    return ResponseEntity.ok(HardwareAssetResponse.fromEntity(asset));
  }

  @GetMapping
  @Operation(summary = "Get all hardware assets")
  public ResponseEntity<List<HardwareAssetResponse>> getAllAssets() {
    log.info("Fetching all hardware assets");

    List<HardwareAssetResponse> assets = assetService.getAllAssets()
        .stream()
        .map(HardwareAssetResponse::fromEntity)
        .toList();

    return ResponseEntity.ok(assets);
  }

  @GetMapping("/tag/{assetTag}")
  @Operation(summary = "Get hardware asset by asset tag")
  public ResponseEntity<HardwareAssetResponse> getAssetByTag(
      @PathVariable String assetTag
  ) {
    log.info("Fetching hardware asset by tag: {}", assetTag);

    HardwareAsset asset = assetService.getAssetByTag(assetTag);
    return ResponseEntity.ok(HardwareAssetResponse.fromEntity(asset));
  }

  @GetMapping("/serial/{serialNumber}")
  @Operation(summary = "Get hardware asset by serial number")
  public ResponseEntity<HardwareAssetResponse> getAssetBySerialNumber(
      @PathVariable String serialNumber
  ) {
    log.info("Fetching hardware asset by serial number: {}", serialNumber);

    HardwareAsset asset = assetService.getAssetBySerialNumber(serialNumber);
    return ResponseEntity.ok(HardwareAssetResponse.fromEntity(asset));
  }

  @PutMapping("/{id}/status")
  @Operation(summary = "Update hardware asset status")
  public ResponseEntity<HardwareAssetResponse> updateAssetStatus(
      @PathVariable UUID id,
      @RequestParam AssetStatus status
  ) {
    log.info("Updating hardware asset status: {} -> {}", id, status);

    HardwareAsset asset = assetService.updateAssetStatus(id, status, "system");
    return ResponseEntity.ok(HardwareAssetResponse.fromEntity(asset));
  }

  @PutMapping("/{id}/assign")
  @Operation(summary = "Assign hardware asset to a user")
  public ResponseEntity<HardwareAssetResponse> assignAsset(
      @PathVariable UUID id,
      @RequestParam String assignee
  ) {
    log.info("Assigning hardware asset {} to {}", id, assignee);

    HardwareAsset asset = assetService.assignAsset(id, assignee, "system");
    return ResponseEntity.ok(HardwareAssetResponse.fromEntity(asset));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete hardware asset")
  public ResponseEntity<Void> deleteAsset(@PathVariable UUID id) {
    log.info("Deleting hardware asset: {}", id);

    assetService.deleteAsset(id);
    return ResponseEntity.noContent().build();
  }
}
