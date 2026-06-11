package com.stockflow.marketdata.repository;

import com.stockflow.marketdata.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
    Optional<Stock> findBySymbol(String symbol);
    List<Stock> findBySectorIgnoreCase(String sector);
    List<Stock> findByIsActiveTrue();
    List<Stock> findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCase(String name, String symbol);
    List<Stock> findByExchange(String exchange);
}
