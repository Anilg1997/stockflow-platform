package com.stockflow.sip.repository;

import com.stockflow.sip.model.Sip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SipRepository extends JpaRepository<Sip, UUID> {
    List<Sip> findByUserIdOrderByCreatedAtDesc(UUID userId);
    List<Sip> findByStatusAndNextExecutionLessThanEqual(String status, LocalDate date);
}
