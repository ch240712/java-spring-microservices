package com.sm.cartservice.service;

import com.sm.cartservice.dto.CustomerCartResponseDTO;
import com.sm.cartservice.model.CartItem;
import com.sm.cartservice.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository; // Mock the repository used by the service

    @InjectMocks
    private CartService cartService;

    @Test
    public void getCartItemsByCustomer() {
        UUID customerId = UUID.fromString("223e4567-e89b-12d3-a456-426614174008");

        List<CartItem> cartItems = new ArrayList();
        Mockito.when(cartRepository.findByCustomerId(customerId)).thenReturn(cartItems);

        CustomerCartResponseDTO customerCartResponseDTO = cartService.getCartItemsByCustomerId(customerId);
        assert(customerCartResponseDTO.getCustomerId().equals(customerId));
    }
}
