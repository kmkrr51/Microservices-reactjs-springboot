package com.snowrepo.cmdb.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationItemRepository extends JpaRepository<ConfigurationItem, UUID> {

  Optional<ConfigurationItem> findByName(String name);
}
