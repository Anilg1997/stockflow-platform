package com.stockflow.execution.repository;

import com.stockflow.execution.model.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TradeRepository extends JpaRepository<Trade, UUID> {
    List<Trade> findByUserIdOrderByTradeDateDesc(UUID userId);
    Page<Trade> findByUserId(UUID userId, Pageable pageable);
    List<Trade> findByOrderId(UUID orderId);
}
