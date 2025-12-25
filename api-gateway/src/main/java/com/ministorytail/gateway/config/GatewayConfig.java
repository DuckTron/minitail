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
                                .route("auth", r -> r
                                                .path("/auth/**")
                                                .filters(f -> f.stripPrefix(1))
                                                .uri("lb://AUTH-SERVICE"))

                                .route("profile", r -> r
                                                .path("/profile/**")
                                                .filters(f -> f.stripPrefix(1))
                                                .uri("lb://PROFILE-SERVICE"))

                                .route("stories", r -> r
                                                .path("/stories/**")
                                                .filters(f -> f.stripPrefix(1))
                                                .uri("lb://STORY-SERVICE"))

                                .route("notifications", r -> r
                                                .path("/notifications/**")
                                                .filters(f -> f.stripPrefix(1))
                                                .uri("lb://NOTIFICATION-SERVICE"))

                                .build();
        }
}