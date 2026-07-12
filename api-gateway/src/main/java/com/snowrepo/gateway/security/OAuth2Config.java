package com.snowrepo.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * OAuth 2.0 and security configuration for API Gateway.
 */
@Configuration
@EnableWebFluxSecurity
public class OAuth2Config {

  /**
   * Configures security filter chain.
   *
   * @param http the ServerHttpSecurity
   * @return the SecurityWebFilterChain
   */
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http
        .csrf().disable()
        .authorizeExchange()
        .pathMatchers("/actuator/**").permitAll()
        .pathMatchers("/health").permitAll()
        .pathMatchers("/api/**").authenticated()
        .anyExchange().permitAll()
        .and()
        .oauth2ResourceServer()
        .jwt();

    return http.build();
  }
}
