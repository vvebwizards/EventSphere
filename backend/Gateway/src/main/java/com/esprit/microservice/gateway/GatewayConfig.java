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
                        .uri("lb://reclamation-management")) // Use Eureka service ID
                // Route for Event microservice
                .route("event", r -> r.path("/event/**")
                        .uri("lb://event-management")) // Update to use Eureka service ID
                // Route for Partnership microservice
                .route("partnership", r -> r.path("/partnership/**")
                        .uri("lb://partnership-management")) // Update if applicable
                // Route for Resource microservice
                .route("resource", r -> r.path("/resource/**")
                        .uri("lb://resource-management")) // Update to use Eureka service ID
                // Route for Payment microservice
                .route("payment", r -> r.path("/payment/**")
                        .uri("lb://payment-management")) // Update to use Eureka service ID
                .build();
    }
}