package com.esprit.microservice.eventmanagement.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.apache.catalina.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openApiInformation() {
        Contact contact = new Contact().email("springdoc@gmail.com").name("Nour Trabelsi");
        Info info = new Info().contact(contact).description("Spring Boot Event Project")
                .summary("Manage Application with web services")
                .title("Study Case Event").version("1.0.0")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
        return new OpenAPI().info(info);
    }
}
