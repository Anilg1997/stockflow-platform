package com.stockflow.analytics.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema = "analytics_schema", name = "portfolio_performance",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"}))
public class PortfolioPerformance {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false) private UUID userId;
    @Column(nullable = false) private LocalDate date;
    @Column(name = "total_invested", nullable = false) private BigDecimal totalInvested;
    @Column(name = "total_current_value", nullable = false) private BigDecimal totalCurrentValue;
    @Column(name = "daily_pnl") private BigDecimal dailyPnl;
    @Column(name = "total_pnl") private BigDecimal totalPnl;
    private BigDecimal xirr;
    @Column(name = "created_at") private Instant createdAt;
    @PrePersist
    protected void onCreate() { createdAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public BigDecimal getTotalInvested() { return totalInvested; }
    public void setTotalInvested(BigDecimal totalInvested) { this.totalInvested = totalInvested; }
    public BigDecimal getTotalCurrentValue() { return totalCurrentValue; }
    public void setTotalCurrentValue(BigDecimal totalCurrentValue) { this.totalCurrentValue = totalCurrentValue; }
    public BigDecimal getDailyPnl() { return dailyPnl; }
    public void setDailyPnl(BigDecimal dailyPnl) { this.dailyPnl = dailyPnl; }
    public BigDecimal getTotalPnl() { return totalPnl; }
    public void setTotalPnl(BigDecimal totalPnl) { this.totalPnl = totalPnl; }
    public BigDecimal getXirr() { return xirr; }
    public void setXirr(BigDecimal xirr) { this.xirr = xirr; }
    public Instant getCreatedAt() { return createdAt; }
}
