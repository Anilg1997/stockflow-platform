package com.stockflow.holdings.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema = "holdings_schema", name = "realized_pnl")
public class RealizedPnl {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(nullable = false, length = 20)
    private String symbol;
    @Column(nullable = false)
    private int quantity;
    @Column(name = "buy_price", nullable = false)
    private BigDecimal buyPrice;
    @Column(name = "sell_price", nullable = false)
    private BigDecimal sellPrice;
    @Column(nullable = false)
    private BigDecimal pnl;
    @Column(name = "financial_year", nullable = false, length = 9)
    private String financialYear;
    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;
    @Column(name = "created_at")
    private Instant createdAt;
    @PrePersist
    protected void onCreate() { createdAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getBuyPrice() { return buyPrice; }
    public void setBuyPrice(BigDecimal buyPrice) { this.buyPrice = buyPrice; }
    public BigDecimal getSellPrice() { return sellPrice; }
    public void setSellPrice(BigDecimal sellPrice) { this.sellPrice = sellPrice; }
    public BigDecimal getPnl() { return pnl; }
    public void setPnl(BigDecimal pnl) { this.pnl = pnl; }
    public String getFinancialYear() { return financialYear; }
    public void setFinancialYear(String financialYear) { this.financialYear = financialYear; }
    public LocalDate getTradeDate() { return tradeDate; }
    public void setTradeDate(LocalDate tradeDate) { this.tradeDate = tradeDate; }
    public Instant getCreatedAt() { return createdAt; }
}
