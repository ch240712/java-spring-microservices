package com.sm.cartservice.controller;

import com.sm.cartservice.dto.CustomerCartResponseDTO;
import com.sm.cartservice.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CartService cartService; // Mock the service used by the controller

    @Test
    public void getCartItemsByCustomerId() throws Exception {
        UUID customerId = UUID.fromString("223e4567-e89b-12d3-a456-426614174008");

        CustomerCartResponseDTO customerCartResponseDTO = new CustomerCartResponseDTO();
        Mockito.when(cartService.getCartItemsByCustomerId(customerId)).thenReturn(customerCartResponseDTO);

        mvc.perform(MockMvcRequestBuilders
                            .get("/cart/" + customerId)
                            .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

