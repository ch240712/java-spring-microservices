package com.sm.productservice.service;

import com.sm.productservice.dto.CustomerProductResponseDTO;
import com.sm.productservice.model.Product;
import com.sm.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public CustomerProductResponseDTO getProductsByIds(List<UUID> productIds) {
        List<Product> products = productRepository.findAllById(productIds);

        CustomerProductResponseDTO customerProductResponseDTO = new CustomerProductResponseDTO();
        customerProductResponseDTO.setProducts(products);

        return customerProductResponseDTO;
    }
}
