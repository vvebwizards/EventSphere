package com.esprit.microservice.partnershipmanagement.config;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class KeycloakConfig {
    @Bean
    public KeycloakSpringBootConfigResolver
    keycloakSpringBootConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    static Keycloak keycloak=null;
    final static String serverUrl = "http://localhost:8180";
    public final static String realm = "eventsphere-realm";
    public final static String clientId = "partnership-management-client";

    final static String userName = "admin";
    final static String password = "admin";
    public KeycloakConfig() {
    }
    @Bean
    public static Keycloak getInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .resteasyClient(new ResteasyClientBuilderImpl()
                            .connectionPoolSize(10)
                            .build())
                    .build();
        }
        return keycloak;
    }
}