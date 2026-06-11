package com.stockflow.holdings.repository;

import com.stockflow.holdings.model.RealizedPnl;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface RealizedPnlRepository extends JpaRepository<RealizedPnl, UUID> {
    List<RealizedPnl> findByUserIdOrderByTradeDateDesc(UUID userId);
    List<RealizedPnl> findByUserIdAndFinancialYear(UUID userId, String financialYear);
}
