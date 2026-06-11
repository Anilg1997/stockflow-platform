package com.stockflow.mfholdings.repository;

import com.stockflow.mfholdings.model.MfHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MfHoldingRepository extends JpaRepository<MfHolding, UUID> {
    List<MfHolding> findByUserId(UUID userId);
}
