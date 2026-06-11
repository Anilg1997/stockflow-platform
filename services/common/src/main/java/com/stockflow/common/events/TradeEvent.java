package com.stockflow.common.events;

import java.math.BigDecimal;
import java.util.UUID;

public class TradeEvent extends BaseEvent {

    private UUID tradeId;
    private UUID orderId;
    private UUID userId;
    private String symbol;
    private String side;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalCharges;
    private BigDecimal netAmount;

    public TradeEvent() {}

    // ── Getters & Setters ────────────────────────────────────
    public UUID getTradeId() { return tradeId; }
    public void setTradeId(UUID tradeId) { this.tradeId = tradeId; }

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getSide() { return side; }
    public void setSide(String side) { this.side = side; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getTotalCharges() { return totalCharges; }
    public void setTotalCharges(BigDecimal totalCharges) { this.totalCharges = totalCharges; }

    public BigDecimal getNetAmount() { return netAmount; }
    public void setNetAmount(BigDecimal netAmount) { this.netAmount = netAmount; }
}
