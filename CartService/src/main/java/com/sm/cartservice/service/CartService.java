package com.sm.cartservice.service;

import com.sm.cartservice.dto.*;
import com.sm.cartservice.model.CartItem;
import com.sm.cartservice.model.Product;
import com.sm.cartservice.repository.CartRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    public CartService(CartRepository cartRepository, RestClient restClient, DiscoveryClient discoveryClient) {
        this.cartRepository = cartRepository;
        this.restClient = restClient;
        this.discoveryClient = discoveryClient;
    }

    public CustomerCartResponseDTO getCartItemsByCustomerId(UUID customerId) {
        List<CartItem> cartItems = cartRepository.findByCustomerId(customerId);
        CustomerCartResponseDTO customerCartResponseDTO = new CustomerCartResponseDTO();
        customerCartResponseDTO.setCustomerId(customerId);

        // Return early if no items found for given customer
        if (cartItems.isEmpty()) {
            return customerCartResponseDTO;
        }

        // Wrap external call to product service in a circuit breaker
        CircuitBreakerHandler circuitBreakerHandler = new CircuitBreakerHandler();
        CircuitBreaker circuitBreaker = circuitBreakerHandler.getCircuitBreaker();
        CustomerProductResponseDTO productsDTO;

        try {
            productsDTO = circuitBreaker.executeSupplier(() -> getProductDetails(cartItems));
        } catch (CallNotPermittedException e) {
            // Fallback
            customerCartResponseDTO.setError("Product service temporarily unavailable");
            return customerCartResponseDTO;
        } catch (RuntimeException e) {
            // Fallback
            customerCartResponseDTO.setError("Failed to fetch products");
            return customerCartResponseDTO;
        }

        Map<UUID, Product> productMap = productsDTO.getProducts().stream()
                .collect(Collectors.toMap(Product::getId, Function.identity())); // Function.identity() returns the unmodified product object; i.e., no transformation

        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem item : cartItems) {
            Product product = productMap.get(item.getProductId());
            if (product != null) {
                CartItemDTO cartItemDTO = new CartItemDTO();
                cartItemDTO.setProductId(item.getProductId());
                cartItemDTO.setName(product.getName());
                cartItemDTO.setDescription(product.getDescription());
                cartItemDTO.setUnitPrice(product.getPrice());
                cartItemDTO.setCount(item.getCount());
                cartItemDTO.setPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getCount())));
                cartItemDTOs.add(cartItemDTO);
            }
        }

        customerCartResponseDTO.setItems(cartItemDTOs);
        return customerCartResponseDTO;
    }

    private CustomerProductResponseDTO getProductDetails(List<CartItem> cartItems) {
        List<UUID> productIds = new ArrayList<>();
        cartItems.forEach(cartItem -> productIds.add(cartItem.getProductId()));

        // Convert list of product ids to a comma-separated string for the query parameter
        String pIdList = productIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        // Use discovery client to find the service id of the product service using its application name
        ServiceInstance serviceInstance = discoveryClient.getInstances("PRODUCTSERVICE").get(0);

        return restClient
                .get()
                .uri(serviceInstance.getUri() + "/product?ids=" + pIdList)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(CustomerProductResponseDTO.class);
    }
}