package com.snowrepo.cmdb.application;

import com.snowrepo.cmdb.domain.ConfigurationItem;
import com.snowrepo.cmdb.domain.ConfigurationItem.CIStatus;
import com.snowrepo.cmdb.domain.ConfigurationItemRepository;
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
@SuppressWarnings("null")
public class ConfigurationItemService {

  private final ConfigurationItemRepository ciRepository;

  public ConfigurationItem createCI(
      String name,
      String type,
      String description,
      String owner,
      String createdBy
  ) {
    log.info("Creating CI: {}", name);

    ConfigurationItem ci = ConfigurationItem.create(
        name,
        type,
        description,
        owner,
        createdBy
    );

    return ciRepository.save(ci);
  }

  public ConfigurationItem getCI(UUID id) {
    log.info("Fetching CI: {}", id);
    return ciRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("CI not found: " + id));
  }

  public ConfigurationItem getCIByName(String name) {
    log.info("Fetching CI by name: {}", name);
    return ciRepository.findByName(name)
        .orElseThrow(() -> new RuntimeException("CI not found: " + name));
  }

  public List<ConfigurationItem> getAllCIs() {
    log.info("Fetching all CIs");
    return ciRepository.findAll();
  }

  public ConfigurationItem updateCI(UUID id, String name, String description, String updatedBy) {
    log.info("Updating CI: {}", id);

    ConfigurationItem ci = getCI(id);
    ci.update(name, description, updatedBy);

    return ciRepository.save(ci);
  }

  public ConfigurationItem changeCIStatus(UUID id, CIStatus newStatus, String updatedBy) {
    log.info("Changing CI status: {} -> {}", id, newStatus);

    ConfigurationItem ci = getCI(id);
    ci.changeStatus(newStatus, updatedBy);

    return ciRepository.save(ci);
  }

  public void deleteCI(UUID id) {
    log.info("Deleting CI: {}", id);
    ciRepository.deleteById(id);
  }
}
