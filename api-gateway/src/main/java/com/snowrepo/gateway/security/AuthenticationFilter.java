package com.snowrepo.gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Authentication filter for validating JWT and API keys.
 */
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

  private final JwtTokenProvider jwtTokenProvider;
  private final ApiKeyManager apiKeyManager;

  public AuthenticationFilter(JwtTokenProvider jwtTokenProvider, ApiKeyManager apiKeyManager) {
    super(Config.class);
    this.jwtTokenProvider = jwtTokenProvider;
    this.apiKeyManager = apiKeyManager;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
      String apiKey = exchange.getRequest().getHeaders().getFirst("X-API-Key");

      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
          return onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
        }
      } else if (apiKey != null) {
        if (!apiKeyManager.validateApiKey(apiKey)) {
          return onError(exchange, "Invalid API key", HttpStatus.UNAUTHORIZED);
        }
      } else {
        return onError(exchange, "Missing authentication", HttpStatus.UNAUTHORIZED);
      }

      return chain.filter(exchange);
    };
  }

  private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
    exchange.getResponse().setStatusCode(status);
    return exchange.getResponse().setComplete();
  }

  public static class Config {
  }
}
