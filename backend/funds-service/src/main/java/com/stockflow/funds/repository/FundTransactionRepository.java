package com.stockflow.funds.repository;

import com.stockflow.funds.model.FundTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FundTransactionRepository extends JpaRepository<FundTransaction, UUID> {
    List<FundTransaction> findByUserIdOrderByCreatedAtDesc(UUID userId);
    Page<FundTransaction> findByUserId(UUID userId, Pageable pageable);
    List<FundTransaction> findByUserIdAndType(UUID userId, String type);
}
