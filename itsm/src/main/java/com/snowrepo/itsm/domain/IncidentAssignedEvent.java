package com.snowrepo.itsm.domain;

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
public class IncidentAssignedEvent extends DomainEvent {

  private UUID incidentId;
  private String incidentNumber;
  private String assignedTo;

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
