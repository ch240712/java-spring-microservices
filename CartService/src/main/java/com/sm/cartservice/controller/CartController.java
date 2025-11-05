package com.sm.cartservice.controller;

import com.sm.cartservice.dto.CustomerCartResponseDTO;
import com.sm.cartservice.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "API for managing a customer's shopping cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get shopping cart items by customer id")
    public ResponseEntity<CustomerCartResponseDTO> getCartItemsByCustomer(@PathVariable UUID customerId) {
        CustomerCartResponseDTO customerCart = cartService.getCartItemsByCustomerId(customerId);

        return ResponseEntity.ok().body(customerCart);
    }
}