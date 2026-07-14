package com.snowrepo.itsm.domain;

import com.snowrepo.domain.DomainEvent;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class IncidentCreatedEvent extends DomainEvent {

  private UUID incidentId;
  private String incidentNumber;
  private String title;
  private Incident.Priority priority;
  private Incident.IncidentStatus status;

  public IncidentCreatedEvent() {
    super(UUID.randomUUID(), "Incident");
  }

  @Override
  public String getEventType() {
    return "IncidentCreatedEvent";
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
