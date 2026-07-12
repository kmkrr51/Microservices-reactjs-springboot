package com.snowrepo.itam.domain;

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
public class HardwareAssetStatusChangedEvent extends DomainEvent {

  private UUID assetId;
  private String assetTag;
  private HardwareAsset.AssetStatus newStatus;

  @Override
  public String getEventType() {
    return "HardwareAssetStatusChangedEvent";
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
