package com.autofx.apigateway.gateway.infrastructure.config;

import com.autofx.apigateway.gateway.infrastructure.filters.AuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    private final AuthFilter authFilter;

    public GatewayConfiguration(AuthFilter authFilter){
        this.authFilter = authFilter;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){

        return builder.routes()
                .route(r -> r
                        .path("/api/authentication-autofx/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://authentication-autofx"))
                .build();
    }
}