package com.snowrepo.itom.domain;

import com.snowrepo.domain.DomainEvent;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class AssetStatusChangedEvent extends DomainEvent {

  public AssetStatusChangedEvent() {
    super(UUID.randomUUID(), "DiscoveredAsset");
  }

  private UUID assetId;
  private String assetName;
  private String assetType;
  private DiscoveredAsset.AssetStatus newStatus;

  @Override
  public String getEventType() {
    return "AssetStatusChangedEvent";
  }

  @Override
  public UUID getAggregateId() {
    return assetId;
  }

  @Override
  public String getAggregateType() {
    return "DiscoveredAsset";
  }
}
