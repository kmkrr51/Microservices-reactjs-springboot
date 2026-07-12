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
public class ConfigurationItemUpdatedEvent extends DomainEvent {

  private UUID ciId;
  private String ciName;
  private String ciType;
  private ConfigurationItem.CIStatus status;

  @Override
  public String getEventType() {
    return "ConfigurationItemUpdatedEvent";
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
