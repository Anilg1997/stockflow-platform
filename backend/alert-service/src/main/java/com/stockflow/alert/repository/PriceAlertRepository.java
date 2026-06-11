package com.stockflow.alert.repository;

import com.stockflow.alert.model.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, UUID> {
    List<PriceAlert> findByUserIdOrderByCreatedAtDesc(UUID userId);
    List<PriceAlert> findBySymbolAndIsTriggeredFalse(String symbol);
    List<PriceAlert> findByUserIdAndIsTriggeredFalse(UUID userId);
}
