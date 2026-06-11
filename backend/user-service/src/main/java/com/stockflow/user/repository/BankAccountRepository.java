package com.stockflow.user.repository;

import com.stockflow.user.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    List<BankAccount> findByUserIdOrderByAddedAtDesc(UUID userId);
    Optional<BankAccount> findByUserIdAndIsPrimaryTrue(UUID userId);
}
