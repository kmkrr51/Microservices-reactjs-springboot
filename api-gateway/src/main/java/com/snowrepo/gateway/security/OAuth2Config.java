package com.snowrepo.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
        .cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeExchange(auth -> auth
            .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .pathMatchers("/actuator/**").permitAll()
            .pathMatchers("/health").permitAll()
            .pathMatchers("/api/v1/auth/**").permitAll()
            .pathMatchers("/api/**").authenticated()
            .anyExchange().permitAll()
        )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

    return http.build();
  }
}
