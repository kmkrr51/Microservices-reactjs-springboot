package com.snowrepo.gateway.security;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * API key manager for validating API keys.
 */
@Component
public class ApiKeyManager {

  private static final Map<String, ApiKeyInfo> apiKeys = new HashMap<>();

  static {
    apiKeys.put("test-api-key-123", new ApiKeyInfo("test-api-key-123", "test-service"));
    apiKeys.put("incident-service-key", new ApiKeyInfo("incident-service-key", "incident-service"));
    apiKeys.put("user-service-key", new ApiKeyInfo("user-service-key", "user-service"));
  }

  /**
   * Validates API key.
   *
   * @param apiKey the API key
   * @return true if valid, false otherwise
   */
  public boolean validateApiKey(String apiKey) {
    return apiKeys.containsKey(apiKey);
  }

  /**
   * Gets service name for API key.
   *
   * @param apiKey the API key
   * @return the service name or null
   */
  public String getServiceName(String apiKey) {
    ApiKeyInfo info = apiKeys.get(apiKey);
    return info != null ? info.getServiceName() : null;
  }

  /**
   * API key information.
   */
  public static class ApiKeyInfo {
    private final String apiKey;
    private final String serviceName;

    public ApiKeyInfo(String apiKey, String serviceName) {
      this.apiKey = apiKey;
      this.serviceName = serviceName;
    }

    public String getApiKey() {
      return apiKey;
    }

    public String getServiceName() {
      return serviceName;
    }
  }
}
