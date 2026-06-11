package com.stockflow.holdings.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "holdings_schema", name = "holdings", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "symbol"}))
public class Holding {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(nullable = false, length = 20)
    private String symbol;
    @Column(nullable = false)
    private int quantity;
    @Column(name = "avg_buy_price", nullable = false)
    private BigDecimal avgBuyPrice;
    @Column(name = "invested_amount", nullable = false)
    private BigDecimal investedAmount = BigDecimal.ZERO;
    @Column(name = "last_updated")
    private Instant lastUpdated;
    @PrePersist @PreUpdate
    protected void onUpdate() { lastUpdated = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getAvgBuyPrice() { return avgBuyPrice; }
    public void setAvgBuyPrice(BigDecimal avgBuyPrice) { this.avgBuyPrice = avgBuyPrice; }
    public BigDecimal getInvestedAmount() { return investedAmount; }
    public void setInvestedAmount(BigDecimal investedAmount) { this.investedAmount = investedAmount; }
    public Instant getLastUpdated() { return lastUpdated; }
}
