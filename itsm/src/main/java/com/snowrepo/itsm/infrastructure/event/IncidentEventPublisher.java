package com.snowrepo.itsm.infrastructure.event;

import com.snowrepo.itsm.domain.IncidentAssignedEvent;
import com.snowrepo.itsm.domain.IncidentCreatedEvent;
import com.snowrepo.itsm.domain.IncidentStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("null")
public class IncidentEventPublisher {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private static final String TOPIC = "itsm.incidents";

  public void publishIncidentCreated(IncidentCreatedEvent event) {
    log.info("Publishing IncidentCreatedEvent: {}", event.getIncidentId());
    kafkaTemplate.send(TOPIC, event.getIncidentId().toString(), event);
  }

  public void publishIncidentStatusChanged(IncidentStatusChangedEvent event) {
    log.info("Publishing IncidentStatusChangedEvent: {}", event.getIncidentId());
    kafkaTemplate.send(TOPIC, event.getIncidentId().toString(), event);
  }

  public void publishIncidentAssigned(IncidentAssignedEvent event) {
    log.info("Publishing IncidentAssignedEvent: {}", event.getIncidentId());
    kafkaTemplate.send(TOPIC, event.getIncidentId().toString(), event);
  }
}
