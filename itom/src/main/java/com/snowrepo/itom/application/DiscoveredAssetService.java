package com.snowrepo.itom.application;

import com.snowrepo.itom.domain.DiscoveredAsset;
import com.snowrepo.itom.domain.DiscoveredAsset.AssetStatus;
import com.snowrepo.itom.domain.DiscoveredAssetRepository;
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
public class DiscoveredAssetService {

  private final DiscoveredAssetRepository assetRepository;

  public DiscoveredAsset createAsset(
      String name,
      String assetType,
      String description,
      String ipAddress,
      String hostname,
      String createdBy
  ) {
    log.info("Creating discovered asset: {}", name);

    DiscoveredAsset asset = DiscoveredAsset.create(
        name,
        assetType,
        description,
        ipAddress,
        hostname,
        createdBy
    );

    return assetRepository.save(asset);
  }

  public DiscoveredAsset getAsset(UUID id) {
    log.info("Fetching asset: {}", id);
    return assetRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Asset not found: " + id));
  }

  public DiscoveredAsset getAssetByHostname(String hostname) {
    log.info("Fetching asset by hostname: {}", hostname);
    return assetRepository.findByHostname(hostname)
        .orElseThrow(() -> new RuntimeException("Asset not found: " + hostname));
  }

  public DiscoveredAsset getAssetByIpAddress(String ipAddress) {
    log.info("Fetching asset by IP: {}", ipAddress);
    return assetRepository.findByIpAddress(ipAddress)
        .orElseThrow(() -> new RuntimeException("Asset not found: " + ipAddress));
  }

  public List<DiscoveredAsset> getAllAssets() {
    log.info("Fetching all assets");
    return assetRepository.findAll();
  }

  public DiscoveredAsset updateAssetStatus(UUID id, AssetStatus newStatus, String updatedBy) {
    log.info("Updating asset status: {} -> {}", id, newStatus);

    DiscoveredAsset asset = getAsset(id);
    asset.updateStatus(newStatus, updatedBy);

    return assetRepository.save(asset);
  }

  public DiscoveredAsset recordAssetDiscovery(UUID id, String updatedBy) {
    log.info("Recording asset discovery: {}", id);

    DiscoveredAsset asset = getAsset(id);
    asset.recordDiscovery(updatedBy);

    return assetRepository.save(asset);
  }

  public void deleteAsset(UUID id) {
    log.info("Deleting asset: {}", id);
    assetRepository.deleteById(id);
  }
}
