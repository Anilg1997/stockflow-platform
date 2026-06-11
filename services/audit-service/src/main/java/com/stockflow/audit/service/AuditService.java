package com.stockflow.audit.service;

import com.stockflow.audit.model.AuditLog;
import com.stockflow.audit.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AuditService {
    private final AuditLogRepository repo;
    public AuditService(AuditLogRepository repo) { this.repo = repo; }
    public AuditLog log(AuditLog log) { return repo.save(log); }
    public Page<AuditLog> getUserAuditLogs(UUID userId, Pageable pageable) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }
    public Page<AuditLog> getByAction(String action, Pageable pageable) {
        return repo.findByAction(action, pageable);
    }
    public Page<AuditLog> getByService(String service, Pageable pageable) {
        return repo.findByService(service, pageable);
    }
}
