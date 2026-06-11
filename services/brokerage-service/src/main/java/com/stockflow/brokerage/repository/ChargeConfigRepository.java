package com.stockflow.brokerage.repository;

import com.stockflow.brokerage.model.ChargeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ChargeConfigRepository extends JpaRepository<ChargeConfig, UUID> {
    Optional<ChargeConfig> findByChargeTypeAndIsActiveTrue(String chargeType);
}
