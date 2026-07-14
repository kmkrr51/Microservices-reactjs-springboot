package com.snowrepo.itsm.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

  private String token;
  private UserInfo user;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UserInfo {

    private String id;
    private String email;
    private String name;
    private String role;
  }
}
