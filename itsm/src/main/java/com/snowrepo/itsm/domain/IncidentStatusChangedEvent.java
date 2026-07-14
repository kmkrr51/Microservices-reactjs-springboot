package com.snowrepo.itsm.domain;

import com.snowrepo.domain.DomainEvent;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class IncidentStatusChangedEvent extends DomainEvent {

  private UUID incidentId;
  private String incidentNumber;
  private Incident.IncidentStatus newStatus;

  public IncidentStatusChangedEvent() {
    super(UUID.randomUUID(), "Incident");
  }

  @Override
  public String getEventType() {
    return "IncidentStatusChangedEvent";
  }

  @Override
  public UUID getAggregateId() {
    return incidentId;
  }

  @Override
  public String getAggregateType() {
    return "Incident";
  }
}
