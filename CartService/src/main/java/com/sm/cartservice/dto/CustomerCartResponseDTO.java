package com.sm.cartservice.dto;

import java.util.List;
import java.util.UUID;

public class CustomerCartResponseDTO {
    private UUID customerId;
    private List<CartItemDTO> items;
    private String error;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
