package com.esprit.microservice.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route for Reclamation microservice
                .route("reclamation", r -> r.path("/reclamation/**")
                        .uri("http://localhost:8084"))
                // Route for Event microservice
                .route("event", r -> r.path("/event/**")
                        .uri("http://localhost:8085"))
                // Route for Partnership microservice
                .route("partnership", r -> r.path("/partnership/**")
                        .uri("http://localhost:8086"))
                // Route for Resource microservice
                .route("resource", r -> r.path("/resource/**")
                        .uri("http://localhost:8087"))
                // Route for Payment microservice
                .route("payment", r -> r.path("/payment/**")
                        .uri("http://localhost:8088"))
                .build();
    }
}
