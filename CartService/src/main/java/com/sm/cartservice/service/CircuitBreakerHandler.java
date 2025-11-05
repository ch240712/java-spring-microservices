package com.sm.cartservice.service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

import java.time.Duration;

public class CircuitBreakerHandler {
    private final CircuitBreakerConfig config;
    private final CircuitBreaker circuitBreaker;

    public CircuitBreakerHandler() {
        // Create custom CircuitBreaker configuration
        config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50.0f)           // Open (break) circuit if 50% of calls fail
                .minimumNumberOfCalls(5)               // Evaluate after at least 5 calls
                .waitDurationInOpenState(Duration.ofSeconds(10)) // Stay open for 10s
                .slidingWindowSize(10)                 // Track last 10 calls
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();

        // Create a CircuitBreaker using the config
        circuitBreaker = CircuitBreaker.of("myService", config);
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreaker;
    }
}
