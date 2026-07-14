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
public class AssetRediscoveredEvent extends DomainEvent {

  public AssetRediscoveredEvent() {
    super(UUID.randomUUID(), "DiscoveredAsset");
  }

  private UUID assetId;
  private String assetName;
  private String assetType;

  @Override
  public String getEventType() {
    return "AssetRediscoveredEvent";
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
