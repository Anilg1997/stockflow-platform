package com.stockflow.analytics.service;

import com.stockflow.analytics.model.PortfolioPerformance;
import com.stockflow.analytics.repository.PortfolioPerformanceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class AnalyticsService {
    private final PortfolioPerformanceRepository repo;
    public AnalyticsService(PortfolioPerformanceRepository repo) { this.repo = repo; }
    public List<PortfolioPerformance> getPerformance(UUID userId) {
        return repo.findByUserIdOrderByDateDesc(userId);
    }
    public PortfolioPerformance getLatestPerformance(UUID userId) {
        return repo.findTopByUserIdOrderByDateDesc(userId);
    }
}
