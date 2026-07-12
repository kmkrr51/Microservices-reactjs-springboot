package com.snowrepo.cmdb.infrastructure.event;

import com.snowrepo.cmdb.domain.ConfigurationItemCreatedEvent;
import com.snowrepo.cmdb.domain.ConfigurationItemStatusChangedEvent;
import com.snowrepo.cmdb.domain.ConfigurationItemUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfigurationItemEventPublisher {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private static final String TOPIC = "cmdb.events";

  public void publishCICreated(ConfigurationItemCreatedEvent event) {
    log.info("Publishing ConfigurationItemCreatedEvent: {}", event.getCiId());
    kafkaTemplate.send(TOPIC, event.getCiId().toString(), event);
  }

  public void publishCIUpdated(ConfigurationItemUpdatedEvent event) {
    log.info("Publishing ConfigurationItemUpdatedEvent: {}", event.getCiId());
    kafkaTemplate.send(TOPIC, event.getCiId().toString(), event);
  }

  public void publishCIStatusChanged(ConfigurationItemStatusChangedEvent event) {
    log.info("Publishing ConfigurationItemStatusChangedEvent: {}", event.getCiId());
    kafkaTemplate.send(TOPIC, event.getCiId().toString(), event);
  }
}
