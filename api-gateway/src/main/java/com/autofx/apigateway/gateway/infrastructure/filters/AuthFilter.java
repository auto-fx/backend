package com.autofx.apigateway.gateway.infrastructure.filters;

import com.autofx.apigateway.gateway.infrastructure.filters.dtos.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter implements GatewayFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);
    private final WebClient webClient;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final String AUTH_VALIDATE_URL = "http://authentication-autofx/api/authentication-autofx/v1/users/get-user-by-jwt";
    private static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";
    private static final List<String> freePaths = List.of(
            "/api/authentication-autofx/v1/users/sign-in",
            "/api/authentication-autofx/v1/users/sign-up",
            "/api/authentication-autofx/swagger-ui/**",
            "/api/authentication-autofx/v3/api-docs",
            "/api/authentication-autofx/v3/api-docs/**",
            "/api/authentication-autofx/swagger-ui.html",
            "/api/authentication-autofx/swagger-resources/**",
            "/api/authentication-autofx/webjars/**"
    );

    public AuthFilter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        boolean isFree = freePaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
        if (isFree) {
            LOGGER.info("Omitting AuthFilter for public path: {}", path);
            return chain.filter(exchange);
        }

        if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
            return this.onError(exchange);
        }

        final var tokenHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        final var chunks = tokenHeader.split(" ");
        if(chunks.length != 2 || !chunks[0].equals("Bearer")){
            return this.onError(exchange);
        }
        final var token = chunks[1];
        return this.webClient
                .get()
                .uri(AUTH_VALIDATE_URL)
                .header(ACCESS_TOKEN_HEADER_NAME, "Bearer " + token)
                .retrieve()
                .bodyToMono(UserResource.class)
                .map(response->exchange)
                .flatMap(chain::filter);
    }

    private  Mono<Void> onError(ServerWebExchange exchange){
        final var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
