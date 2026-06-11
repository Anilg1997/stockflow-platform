package com.stockflow.execution.service;

import com.stockflow.execution.model.Trade;
import com.stockflow.execution.repository.TradeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TradeService {
    private final TradeRepository repo;
    public TradeService(TradeRepository repo) { this.repo = repo; }
    public List<Trade> getUserTrades(UUID userId) {
        return repo.findByUserIdOrderByTradeDateDesc(userId);
    }
    public Page<Trade> getUserTradesPaged(UUID userId, Pageable pageable) {
        return repo.findByUserId(userId, pageable);
    }
    public Trade getTrade(UUID tradeId) {
        return repo.findById(tradeId).orElseThrow(() -> new IllegalArgumentException("Trade not found"));
    }
    public List<Trade> getTradesByOrder(UUID orderId) {
        return repo.findByOrderId(orderId);
    }
}
