package com.sm.productservice.controller;

import com.sm.productservice.dto.CustomerProductResponseDTO;
import com.sm.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<CustomerProductResponseDTO> getProductDetails(@RequestParam List<UUID> ids) {
        CustomerProductResponseDTO customerProducts = productService.getProductsByIds(ids);

        return ResponseEntity.ok().body(customerProducts);
    }

}
