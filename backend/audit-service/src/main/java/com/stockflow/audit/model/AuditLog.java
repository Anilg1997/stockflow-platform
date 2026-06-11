package com.stockflow.audit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Document(collection = "audit_logs")
public class AuditLog {
    @Id
    private String id;
    private UUID userId;
    private String action;
    private String resource;
    private String resourceId;
    private String service;
    private Map<String, Object> details;
    private String ipAddress;
    private Instant createdAt = Instant.now();
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }
    public String getResourceId() { return resourceId; }
    public void setResourceId(String resourceId) { this.resourceId = resourceId; }
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    public Map<String, Object> getDetails() { return details; }
    public void setDetails(Map<String, Object> details) { this.details = details; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
