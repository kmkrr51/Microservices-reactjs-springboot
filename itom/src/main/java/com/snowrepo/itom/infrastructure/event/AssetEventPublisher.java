package com.snowrepo.itom.infrastructure.event;

import com.snowrepo.itom.domain.AssetDiscoveredEvent;
import com.snowrepo.itom.domain.AssetRediscoveredEvent;
import com.snowrepo.itom.domain.AssetStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssetEventPublisher {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private static final String TOPIC = "itom.assets";

  public void publishAssetDiscovered(AssetDiscoveredEvent event) {
    log.info("Publishing AssetDiscoveredEvent: {}", event.getAssetId());
    kafkaTemplate.send(TOPIC, event.getAssetId().toString(), event);
  }

  public void publishAssetStatusChanged(AssetStatusChangedEvent event) {
    log.info("Publishing AssetStatusChangedEvent: {}", event.getAssetId());
    kafkaTemplate.send(TOPIC, event.getAssetId().toString(), event);
  }

  public void publishAssetRediscovered(AssetRediscoveredEvent event) {
    log.info("Publishing AssetRediscoveredEvent: {}", event.getAssetId());
    kafkaTemplate.send(TOPIC, event.getAssetId().toString(), event);
  }
}
