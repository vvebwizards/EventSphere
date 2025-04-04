package com.esprit.microservice.resourcemanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openApiInformation() {
        return new OpenAPI()
                .info(new Info()
                        .title("Resource Management API")
                        .version("1.0.0")
                        .description("Manage Application with web services for ASI II Course")
                        .contact(new Contact()
                                .name("Wiem Ben Mansour")
                                .email("wiembm@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}