package com.sm.cartservice.repository;

import com.sm.cartservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartItem, UUID> {
    public List<CartItem> findByCustomerId(UUID customerId);
}