package com.snowrepo.itom;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ItomApplication {

  public static void main(String[] args) {
    SpringApplication.run(ItomApplication.class, args);
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("ITOM Service API")
            .version("1.0.0")
            .description("IT Operations Management microservice API"));
  }
}
