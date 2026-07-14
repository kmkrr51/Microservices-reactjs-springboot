package com.snowrepo.cmdb.domain;

import com.snowrepo.domain.DomainEvent;
import java.util.UUID;
public class ConfigurationItemStatusChangedEvent extends DomainEvent {

  private UUID ciId;
  private String ciName;
  private String ciType;
  private ConfigurationItem.CIStatus newStatus;

  public ConfigurationItemStatusChangedEvent() {
    super(UUID.randomUUID(), "ConfigurationItem");
  }

  public ConfigurationItemStatusChangedEvent(UUID ciId, String ciName, String ciType, ConfigurationItem.CIStatus newStatus) {
    super(ciId, "ConfigurationItem");
    this.ciId = ciId;
    this.ciName = ciName;
    this.ciType = ciType;
    this.newStatus = newStatus;
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

  public ConfigurationItem.CIStatus getNewStatus() {
    return newStatus;
  }

  @Override
  public String getEventType() {
    return "ConfigurationItemStatusChangedEvent";
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
