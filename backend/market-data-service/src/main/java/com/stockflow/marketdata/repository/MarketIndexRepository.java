package com.stockflow.marketdata.repository;

import com.stockflow.marketdata.model.MarketIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface MarketIndexRepository extends JpaRepository<MarketIndex, UUID> {
    Optional<MarketIndex> findByName(String name);
}
