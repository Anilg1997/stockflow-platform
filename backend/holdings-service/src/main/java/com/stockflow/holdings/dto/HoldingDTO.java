package com.stockflow.holdings.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class HoldingDTO {
    private UUID id;
    private String symbol;
    private int quantity;
    private BigDecimal avgBuyPrice;
    private BigDecimal investedAmount;
    private BigDecimal currentPrice;
    private BigDecimal currentValue;
    private BigDecimal pnl;
    private BigDecimal pnlPercent;
    private BigDecimal dayChange;
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getAvgBuyPrice() { return avgBuyPrice; }
    public void setAvgBuyPrice(BigDecimal avgBuyPrice) { this.avgBuyPrice = avgBuyPrice; }
    public BigDecimal getInvestedAmount() { return investedAmount; }
    public void setInvestedAmount(BigDecimal investedAmount) { this.investedAmount = investedAmount; }
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
    public BigDecimal getCurrentValue() { return currentValue; }
    public void setCurrentValue(BigDecimal currentValue) { this.currentValue = currentValue; }
    public BigDecimal getPnl() { return pnl; }
    public void setPnl(BigDecimal pnl) { this.pnl = pnl; }
    public BigDecimal getPnlPercent() { return pnlPercent; }
    public void setPnlPercent(BigDecimal pnlPercent) { this.pnlPercent = pnlPercent; }
    public BigDecimal getDayChange() { return dayChange; }
    public void setDayChange(BigDecimal dayChange) { this.dayChange = dayChange; }
}
