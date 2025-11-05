package com.sm.cartservice.dto;

import com.sm.cartservice.model.Product;

import java.util.Collections;
import java.util.List;

public class CustomerProductResponseDTO {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}