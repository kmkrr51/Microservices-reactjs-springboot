package com.snowrepo.itam.domain;

import com.snowrepo.domain.DomainEvent;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class HardwareAssetCreatedEvent extends DomainEvent {

  public HardwareAssetCreatedEvent() {
    super(UUID.randomUUID(), "HardwareAsset");
  }

  private UUID assetId;
  private String assetTag;
  private String assetName;
  private String assetType;
  private HardwareAsset.AssetStatus status;

  @Override
  public String getEventType() {
    return "HardwareAssetCreatedEvent";
  }

  @Override
  public UUID getAggregateId() {
    return assetId;
  }

  @Override
  public String getAggregateType() {
    return "HardwareAsset";
  }
}
