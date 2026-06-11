package com.stockflow.order.repository;

import com.stockflow.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserIdOrderByPlacedAtDesc(UUID userId);
    Page<Order> findByUserId(UUID userId, Pageable pageable);
    List<Order> findByUserIdAndSymbol(UUID userId, String symbol);
    List<Order> findByStatus(String status);
}
