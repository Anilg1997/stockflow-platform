package com.stockflow.user.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "user_schema", name = "nominees")
public class Nominee {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(nullable = false, length = 150)
    private String name;
    @Column(nullable = false, length = 50)
    private String relation;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal percentage;
    @Column(name = "created_at")
    private Instant createdAt;
    @PrePersist
    protected void onCreate() { createdAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }
    public BigDecimal getPercentage() { return percentage; }
    public void setPercentage(BigDecimal percentage) { this.percentage = percentage; }
    public Instant getCreatedAt() { return createdAt; }
}
