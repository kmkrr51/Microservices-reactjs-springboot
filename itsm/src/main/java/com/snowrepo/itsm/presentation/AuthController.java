package com.snowrepo.itsm.presentation;

import com.snowrepo.itsm.presentation.dto.LoginRequest;
import com.snowrepo.itsm.presentation.dto.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthController {

  @PostMapping("/login")
  @Operation(summary = "Login user")
  public ResponseEntity<AuthResponse> login(
      @Valid @RequestBody LoginRequest request
  ) {
    log.info("Login attempt for user: {}", request.getEmail());

    AuthResponse response = AuthResponse.builder()
        .token("mock-jwt-token-" + System.currentTimeMillis())
        .user(new AuthResponse.UserInfo(
            "user-" + System.currentTimeMillis(),
            request.getEmail(),
            request.getEmail().split("@")[0],
            "ADMIN"
        ))
        .build();

    return ResponseEntity.ok(response);
  }

  @GetMapping("/me")
  @Operation(summary = "Get current user")
  public ResponseEntity<AuthResponse.UserInfo> getCurrentUser() {
    log.info("Fetching current user");

    AuthResponse.UserInfo user = new AuthResponse.UserInfo(
        "user-123",
        "admin@example.com",
        "admin",
        "ADMIN"
    );

    return ResponseEntity.ok(user);
  }
}
