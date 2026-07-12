package com.snowrepo.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway configuration for routing and filters.
 */
@Configuration
public class GatewayConfig {

  /**
   * Configures routes for microservices.
   *
   * @param builder the route locator builder
   * @return the route locator
   */
  @Bean
  public RouteLocator routes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("incident-service", r -> r
            .path("/api/incidents/**")
            .uri("http://incident-service:8080"))
        .route("user-service", r -> r
            .path("/api/users/**")
            .uri("http://user-service:8080"))
        .route("cmdb-service", r -> r
            .path("/api/cmdb/**")
            .uri("http://cmdb-service:8080"))
        .build();
  }
}
