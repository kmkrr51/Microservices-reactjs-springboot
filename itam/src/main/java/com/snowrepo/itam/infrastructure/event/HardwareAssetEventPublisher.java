package com.snowrepo.itam.infrastructure.event;

import com.snowrepo.itam.domain.HardwareAssetAssignedEvent;
import com.snowrepo.itam.domain.HardwareAssetCreatedEvent;
import com.snowrepo.itam.domain.HardwareAssetStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HardwareAssetEventPublisher {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private static final String TOPIC = "itam.hardware";

  public void publishAssetCreated(HardwareAssetCreatedEvent event) {
    log.info("Publishing HardwareAssetCreatedEvent: {}", event.getAssetId());
    kafkaTemplate.send(TOPIC, event.getAssetId().toString(), event);
  }

  public void publishAssetStatusChanged(HardwareAssetStatusChangedEvent event) {
    log.info("Publishing HardwareAssetStatusChangedEvent: {}", event.getAssetId());
    kafkaTemplate.send(TOPIC, event.getAssetId().toString(), event);
  }

  public void publishAssetAssigned(HardwareAssetAssignedEvent event) {
    log.info("Publishing HardwareAssetAssignedEvent: {}", event.getAssetId());
    kafkaTemplate.send(TOPIC, event.getAssetId().toString(), event);
  }
}
