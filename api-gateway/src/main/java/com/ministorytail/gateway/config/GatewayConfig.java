package com.ministorytail.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/auth-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://AUTH-SERVICE"))

                .route("profile-service", r -> r
                        .path("/profile-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://PROFILE-SERVICE"))

                .route("story-service", r -> r
                        .path("/story-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://STORY-SERVICE"))

                .route("notification-service", r -> r
                        .path("/notification-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://NOTIFICATION-SERVICE"))

                .build();
    }
}