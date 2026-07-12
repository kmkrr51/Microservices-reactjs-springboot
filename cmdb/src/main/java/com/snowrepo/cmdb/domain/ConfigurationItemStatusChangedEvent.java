package com.snowrepo.cmdb.domain;

import com.snowrepo.domain.DomainEvent;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationItemStatusChangedEvent extends DomainEvent {

  private UUID ciId;
  private String ciName;
  private String ciType;
  private ConfigurationItem.CIStatus newStatus;

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
