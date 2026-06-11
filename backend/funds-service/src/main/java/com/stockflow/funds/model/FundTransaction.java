package com.stockflow.funds.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "funds_schema", name = "fund_transactions")
public class FundTransaction {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(nullable = false, length = 30)
    private String type;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(length = 100)
    private String reference;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(length = 20)
    private String status = "PENDING";
    @Column(name = "created_at")
    private Instant createdAt;
    @PrePersist
    protected void onCreate() { createdAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
}
