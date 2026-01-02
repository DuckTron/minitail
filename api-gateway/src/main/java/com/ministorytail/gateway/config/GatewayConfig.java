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
                                                .filters(f -> f
                                                                .stripPrefix(1)
                                                                .circuitBreaker(c -> c
                                                                                .setName("authCircuitBreaker")
                                                                                .setFallbackUri("forward:/fallback/auth")))
                                                .uri("lb://AUTH-SERVICE"))

                                .route("profile", r -> r
                                                .path("/profile/**")
                                                .filters(f -> f
                                                                .stripPrefix(1)
                                                                .circuitBreaker(c -> c
                                                                                .setName("profileCircuitBreaker")
                                                                                .setFallbackUri("forward:/fallback/profile")))
                                                .uri("lb://PROFILE-SERVICE"))

                                .route("prompts", r -> r
                                                .path("/prompts/**")
                                                .filters(f -> f
                                                                .stripPrefix(1)
                                                                .circuitBreaker(c -> c
                                                                                .setName("promptCircuitBreaker")
                                                                                .setFallbackUri("forward:/fallback/prompts")))
                                                .uri("lb://PROMPT-SERVICE"))

                                .route("stories", r -> r
                                                .path("/stories/**")
                                                .filters(f -> f
                                                                .stripPrefix(1)
                                                                .circuitBreaker(c -> c
                                                                                .setName("storyCircuitBreaker")
                                                                                .setFallbackUri("forward:/fallback/stories")))
                                                .uri("lb://STORY-SERVICE"))

                                .route("notifications", r -> r
                                                .path("/notifications/**")
                                                .filters(f -> f
                                                                .stripPrefix(1)
                                                                .circuitBreaker(c -> c
                                                                                .setName("notificationCircuitBreaker")
                                                                                .setFallbackUri("forward:/fallback/notifications")))
                                                .uri("lb://NOTIFICATION-SERVICE"))

                                .build();
        }
}
