package com.snowrepo.itsm.domain;

import com.snowrepo.domain.DomainEvent;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class IncidentAssignedEvent extends DomainEvent {

  private UUID incidentId;
  private String incidentNumber;
  private String assignedTo;

  public IncidentAssignedEvent() {
    super(UUID.randomUUID(), "Incident");
  }

  @Override
  public String getEventType() {
    return "IncidentAssignedEvent";
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
