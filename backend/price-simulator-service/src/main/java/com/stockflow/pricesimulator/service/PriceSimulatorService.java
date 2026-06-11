package com.stockflow.pricesimulator.service;

import com.stockflow.common.constants.ServiceConstants;
import com.stockflow.common.events.PriceEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Random;

@Service
public class PriceSimulatorService {
    private final KafkaTemplate<String, PriceEvent> kafkaTemplate;
    private final EntityManager entityManager;
    private final Random random = new Random();

    public PriceSimulatorService(KafkaTemplate<String, PriceEvent> kafkaTemplate, EntityManager entityManager) {
        this.kafkaTemplate = kafkaTemplate;
        this.entityManager = entityManager;
    }

    @Scheduled(fixedRate = 2000)
    public void simulatePriceUpdates() {
        Query query = entityManager.createNativeQuery(
            "SELECT symbol, current_price FROM market_schema.stock_prices ORDER BY random() LIMIT 10");
        List<Object[]> results = query.getResultList();

        for (Object[] row : results) {
            String symbol = (String) row[0];
            double currentPrice = ((Number) row[1]).doubleValue();
            double changePercent = (random.nextDouble() - 0.5) * 2.0;
            double newPrice = currentPrice * (1 + changePercent / 100);
            long volume = (long) (random.nextDouble() * 100000 + 1000);

            BigDecimal cPrice = BigDecimal.valueOf(newPrice).setScale(2, RoundingMode.HALF_UP);
            BigDecimal change = cPrice.subtract(BigDecimal.valueOf(currentPrice)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal cPct = BigDecimal.valueOf(changePercent).setScale(4, RoundingMode.HALF_UP);

            entityManager.createNativeQuery(
                "UPDATE market_schema.stock_prices SET current_price = ?, change = ?, change_percent = ?, volume = ?, updated_at = ? WHERE symbol = ?")
                .setParameter(1, cPrice)
                .setParameter(2, change)
                .setParameter(3, cPct)
                .setParameter(4, volume)
                .setParameter(5, Instant.now())
                .setParameter(6, symbol)
                .executeUpdate();

            PriceEvent event = new PriceEvent();
            event.setSymbol(symbol);
            event.setCurrentPrice(cPrice);
            event.setChange(change);
            event.setChangePercent(cPct);
            event.setVolume(volume);
            event.setUpdatedAt(Instant.now());
            kafkaTemplate.send(ServiceConstants.TOPIC_PRICE_UPDATED, symbol, event);
        }
    }
}
