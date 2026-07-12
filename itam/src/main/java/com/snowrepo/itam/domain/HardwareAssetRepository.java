package com.snowrepo.itam.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HardwareAssetRepository extends JpaRepository<HardwareAsset, UUID> {

  Optional<HardwareAsset> findByAssetTag(String assetTag);

  Optional<HardwareAsset> findBySerialNumber(String serialNumber);
}
