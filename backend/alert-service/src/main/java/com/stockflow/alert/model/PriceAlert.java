package com.stockflow.alert.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "alert_schema", name = "price_alerts")
public class PriceAlert {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false) private UUID userId;
    @Column(nullable = false, length = 20) private String symbol;
    @Column(name = "target_price", nullable = false) private BigDecimal targetPrice;
    @Column(nullable = false, length = 10) private String condition;
    @Column(name = "is_triggered") private boolean isTriggered;
    @Column(name = "created_at") private Instant createdAt;
    @Column(name = "triggered_at") private Instant triggeredAt;
    @PrePersist
    protected void onCreate() { createdAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public BigDecimal getTargetPrice() { return targetPrice; }
    public void setTargetPrice(BigDecimal targetPrice) { this.targetPrice = targetPrice; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public boolean isTriggered() { return isTriggered; }
    public void setTriggered(boolean triggered) { isTriggered = triggered; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getTriggeredAt() { return triggeredAt; }
    public void setTriggeredAt(Instant triggeredAt) { this.triggeredAt = triggeredAt; }
}
