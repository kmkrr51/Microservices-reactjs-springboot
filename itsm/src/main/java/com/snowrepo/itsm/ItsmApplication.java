package com.snowrepo.itsm;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ItsmApplication {

  public static void main(String[] args) {
    SpringApplication.run(ItsmApplication.class, args);
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("ITSM Service API")
            .version("1.0.0")
            .description("IT Service Management microservice API"));
  }
}
