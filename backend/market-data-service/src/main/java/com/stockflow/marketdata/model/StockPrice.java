package com.stockflow.marketdata.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "market_schema", name = "stock_prices")
public class StockPrice {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false, length = 20)
    private String symbol;
    @Column(name = "current_price", nullable = false)
    private BigDecimal currentPrice;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private Long volume = 0L;
    @Column(name = "week_high_52")
    private BigDecimal weekHigh52;
    @Column(name = "week_low_52")
    private BigDecimal weekLow52;
    private BigDecimal change;
    @Column(name = "change_percent")
    private BigDecimal changePercent;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @PrePersist @PreUpdate
    protected void onUpdate() { updatedAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
    public BigDecimal getOpen() { return open; }
    public void setOpen(BigDecimal open) { this.open = open; }
    public BigDecimal getHigh() { return high; }
    public void setHigh(BigDecimal high) { this.high = high; }
    public BigDecimal getLow() { return low; }
    public void setLow(BigDecimal low) { this.low = low; }
    public BigDecimal getClose() { return close; }
    public void setClose(BigDecimal close) { this.close = close; }
    public Long getVolume() { return volume; }
    public void setVolume(Long volume) { this.volume = volume; }
    public BigDecimal getWeekHigh52() { return weekHigh52; }
    public void setWeekHigh52(BigDecimal weekHigh52) { this.weekHigh52 = weekHigh52; }
    public BigDecimal getWeekLow52() { return weekLow52; }
    public void setWeekLow52(BigDecimal weekLow52) { this.weekLow52 = weekLow52; }
    public BigDecimal getChange() { return change; }
    public void setChange(BigDecimal change) { this.change = change; }
    public BigDecimal getChangePercent() { return changePercent; }
    public void setChangePercent(BigDecimal changePercent) { this.changePercent = changePercent; }
    public Instant getUpdatedAt() { return updatedAt; }
}
