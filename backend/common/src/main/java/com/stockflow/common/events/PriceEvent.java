package com.stockflow.common.events;

import java.math.BigDecimal;
import java.time.Instant;

public class PriceEvent extends BaseEvent {

    private String symbol;
    private BigDecimal currentPrice;
    private BigDecimal change;
    private BigDecimal changePercent;
    private BigDecimal high;
    private BigDecimal low;
    private long volume;
    private Instant updatedAt;

    public PriceEvent() {}

    // ── Getters & Setters ────────────────────────────────────
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }

    public BigDecimal getChange() { return change; }
    public void setChange(BigDecimal change) { this.change = change; }

    public BigDecimal getChangePercent() { return changePercent; }
    public void setChangePercent(BigDecimal changePercent) { this.changePercent = changePercent; }

    public BigDecimal getHigh() { return high; }
    public void setHigh(BigDecimal high) { this.high = high; }

    public BigDecimal getLow() { return low; }
    public void setLow(BigDecimal low) { this.low = low; }

    public long getVolume() { return volume; }
    public void setVolume(long volume) { this.volume = volume; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
