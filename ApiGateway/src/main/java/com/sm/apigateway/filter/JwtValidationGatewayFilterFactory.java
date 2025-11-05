package com.sm.apigateway.filter;

import com.sm.apigateway.service.ApiGatewayService;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Locale;

// Filter class for intercepting HTTP requests, apply custom logic and decide whether to continue
// processing the request or cancel it
@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    // Extending AbstractGatewayFilterFactory tells Spring Boot that we want to add
    // this filter to the request lifecycle
    private final ApiGatewayService apiGatewayService;
    private final WebClient webClient; // Allows HTTP requests to the auth service

    public JwtValidationGatewayFilterFactory(ApiGatewayService apiGatewayService, WebClient.Builder webClientBuilder) {
        this.apiGatewayService = apiGatewayService;
        this.webClient = webClientBuilder.baseUrl(apiGatewayService.getAuthServiceUri()).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> { // exchange variable holds the Java object for the current request
            // Check authorization token in request
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (token == null || !token.toLowerCase(Locale.ROOT).startsWith("bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete(); // Return early for empty/invalid token
            }

            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve() // Get response
                    .toBodilessEntity()
                    .then(chain.filter(exchange)); // Continue request down the chain or to destination
        };
    }
}
