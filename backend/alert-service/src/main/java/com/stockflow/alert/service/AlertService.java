package com.stockflow.alert.service;

import com.stockflow.alert.model.PriceAlert;
import com.stockflow.alert.repository.PriceAlertRepository;
import com.stockflow.common.constants.ServiceConstants;
import com.stockflow.common.events.PriceEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AlertService {
    private final PriceAlertRepository repo;
    public AlertService(PriceAlertRepository repo) { this.repo = repo; }

    @Transactional
    public PriceAlert createAlert(UUID userId, String symbol, BigDecimal targetPrice, String condition) {
        PriceAlert alert = new PriceAlert();
        alert.setUserId(userId);
        alert.setSymbol(symbol.toUpperCase());
        alert.setTargetPrice(targetPrice);
        alert.setCondition(condition);
        return repo.save(alert);
    }

    public List<PriceAlert> getUserAlerts(UUID userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<PriceAlert> getActiveAlerts(UUID userId) {
        return repo.findByUserIdAndIsTriggeredFalse(userId);
    }

    @Transactional
    public void deleteAlert(UUID userId, UUID alertId) {
        PriceAlert alert = repo.findById(alertId)
            .orElseThrow(() -> new IllegalArgumentException("Alert not found"));
        if (!alert.getUserId().equals(userId)) throw new IllegalArgumentException("Access denied");
        repo.delete(alert);
    }

    @KafkaListener(topics = ServiceConstants.TOPIC_PRICE_UPDATED)
    public void checkAlerts(PriceEvent event) {
        List<PriceAlert> alerts = repo.findBySymbolAndIsTriggeredFalse(event.getSymbol());
        for (PriceAlert alert : alerts) {
            boolean shouldTrigger = false;
            if ("ABOVE".equals(alert.getCondition()) &&
                event.getCurrentPrice().compareTo(alert.getTargetPrice()) >= 0) {
                shouldTrigger = true;
            } else if ("BELOW".equals(alert.getCondition()) &&
                       event.getCurrentPrice().compareTo(alert.getTargetPrice()) <= 0) {
                shouldTrigger = true;
            }
            if (shouldTrigger) {
                alert.setTriggered(true);
                alert.setTriggeredAt(Instant.now());
                repo.save(alert);
            }
        }
    }
}
