package com.snowrepo.itam.application;

import com.snowrepo.itam.domain.HardwareAsset;
import com.snowrepo.itam.domain.HardwareAsset.AssetStatus;
import com.snowrepo.itam.domain.HardwareAssetRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HardwareAssetService {

  private final HardwareAssetRepository assetRepository;

  public HardwareAsset createAsset(
      String assetTag,
      String name,
      String assetType,
      String manufacturer,
      String model,
      String serialNumber,
      BigDecimal purchaseCost,
      String createdBy
  ) {
    log.info("Creating hardware asset: {}", assetTag);

    HardwareAsset asset = HardwareAsset.create(
        assetTag,
        name,
        assetType,
        manufacturer,
        model,
        serialNumber,
        purchaseCost,
        createdBy
    );

    return assetRepository.save(asset);
  }

  public HardwareAsset getAsset(UUID id) {
    log.info("Fetching asset: {}", id);
    return assetRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Asset not found: " + id));
  }

  public HardwareAsset getAssetByTag(String assetTag) {
    log.info("Fetching asset by tag: {}", assetTag);
    return assetRepository.findByAssetTag(assetTag)
        .orElseThrow(() -> new RuntimeException("Asset not found: " + assetTag));
  }

  public HardwareAsset getAssetBySerialNumber(String serialNumber) {
    log.info("Fetching asset by serial number: {}", serialNumber);
    return assetRepository.findBySerialNumber(serialNumber)
        .orElseThrow(() -> new RuntimeException("Asset not found: " + serialNumber));
  }

  public List<HardwareAsset> getAllAssets() {
    log.info("Fetching all assets");
    return assetRepository.findAll();
  }

  public HardwareAsset updateAssetStatus(UUID id, AssetStatus newStatus, String updatedBy) {
    log.info("Updating asset status: {} -> {}", id, newStatus);

    HardwareAsset asset = getAsset(id);
    asset.updateStatus(newStatus, updatedBy);

    return assetRepository.save(asset);
  }

  public HardwareAsset assignAsset(UUID id, String assignee, String updatedBy) {
    log.info("Assigning asset {} to {}", id, assignee);

    HardwareAsset asset = getAsset(id);
    asset.assignTo(assignee, updatedBy);

    return assetRepository.save(asset);
  }

  public void deleteAsset(UUID id) {
    log.info("Deleting asset: {}", id);
    assetRepository.deleteById(id);
  }
}
