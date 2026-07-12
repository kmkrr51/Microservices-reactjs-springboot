package com.snowrepo.gateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.RouteLocator;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Gateway Configuration Tests")
class GatewayConfigTest {

  @Autowired
  private RouteLocator routeLocator;

  @Test
  @DisplayName("Should load route locator")
  void testRouteLocatorLoaded() {
    assertNotNull(routeLocator);
  }

  @Test
  @DisplayName("Should have gateway configuration")
  void testGatewayConfiguration() {
    assertNotNull(routeLocator);
  }
}
