package com.snowrepo.itom.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscoveredAssetRepository extends JpaRepository<DiscoveredAsset, UUID> {

  Optional<DiscoveredAsset> findByHostname(String hostname);

  Optional<DiscoveredAsset> findByIpAddress(String ipAddress);
}
