package com.stockflow.analytics.repository;

import com.stockflow.analytics.model.PortfolioPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PortfolioPerformanceRepository extends JpaRepository<PortfolioPerformance, UUID> {
    List<PortfolioPerformance> findByUserIdOrderByDateDesc(UUID userId);
    PortfolioPerformance findTopByUserIdOrderByDateDesc(UUID userId);
}
