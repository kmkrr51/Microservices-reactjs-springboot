package com.snowrepo.cmdb.domain;

import com.snowrepo.domain.DomainEvent;
import java.util.UUID;
public class ConfigurationItemCreatedEvent extends DomainEvent {

  private UUID ciId;
  private String ciName;
  private String ciType;
  private ConfigurationItem.CIStatus status;

  public ConfigurationItemCreatedEvent() {
    super(UUID.randomUUID(), "ConfigurationItem");
  }

  public ConfigurationItemCreatedEvent(UUID ciId, String ciName, String ciType, ConfigurationItem.CIStatus status) {
    super(ciId, "ConfigurationItem");
    this.ciId = ciId;
    this.ciName = ciName;
    this.ciType = ciType;
    this.status = status;
  }

  public UUID getCiId() {
    return ciId;
  }

  public String getCiName() {
    return ciName;
  }

  public String getCiType() {
    return ciType;
  }

  public ConfigurationItem.CIStatus getStatus() {
    return status;
  }

  @Override
  public String getEventType() {
    return "ConfigurationItemCreatedEvent";
  }

  @Override
  public UUID getAggregateId() {
    return ciId;
  }

  @Override
  public String getAggregateType() {
    return "ConfigurationItem";
  }
}
