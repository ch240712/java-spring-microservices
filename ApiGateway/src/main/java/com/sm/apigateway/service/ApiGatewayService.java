package com.sm.apigateway.service;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

@Service
public class ApiGatewayService {
    private final DiscoveryClient discoveryClient;

    public ApiGatewayService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public String getAuthServiceUri() {
        return discoveryClient.getInstances("AUTHSERVICE").get(0).getUri().toString();
    }
}
