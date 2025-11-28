package com.mimi.UserService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI userServiceAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("User Service API")
                .description("Microservicio de usuarios para la app Mimi")
                .version("1.0.0"));
    }
}
