package com.sm.cartservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class CartServiceApplication {
    @Bean
    public RestClient getRestClient() {
        return RestClient.create();
    }

    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }

}