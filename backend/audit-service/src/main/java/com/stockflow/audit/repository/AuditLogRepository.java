package com.stockflow.audit.repository;

import com.stockflow.audit.model.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    Page<AuditLog> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
    Page<AuditLog> findByAction(String action, Pageable pageable);
    Page<AuditLog> findByService(String service, Pageable pageable);
}
