package com.stockflow.marketdata.repository;

import com.stockflow.marketdata.model.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface StockPriceRepository extends JpaRepository<StockPrice, UUID> {
    Optional<StockPrice> findBySymbol(String symbol);
}
