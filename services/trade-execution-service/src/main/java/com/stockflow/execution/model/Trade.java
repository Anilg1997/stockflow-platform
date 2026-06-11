package com.stockflow.execution.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "trade_schema", name = "trades")
public class Trade {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "order_id", nullable = false)
    private UUID orderId;
    @Column(name = "user_id", nullable = false) private UUID userId;
    @Column(nullable = false, length = 20) private String symbol;
    @Column(nullable = false, length = 4) private String side;
    @Column(name = "trade_type", length = 10) private String tradeType;
    @Column(nullable = false) private int quantity;
    @Column(nullable = false) private BigDecimal price;
    private BigDecimal brokerage;
    private BigDecimal stt;
    @Column(name = "exchange_charges") private BigDecimal exchangeCharges;
    private BigDecimal gst;
    @Column(name = "sebi_charges") private BigDecimal sebiCharges;
    @Column(name = "stamp_duty") private BigDecimal stampDuty;
    @Column(name = "total_charges") private BigDecimal totalCharges;
    @Column(name = "net_amount", nullable = false) private BigDecimal netAmount;
    @Column(name = "trade_date") private Instant tradeDate;
    @PrePersist
    protected void onCreate() { tradeDate = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getSide() { return side; }
    public void setSide(String side) { this.side = side; }
    public String getTradeType() { return tradeType; }
    public void setTradeType(String tradeType) { this.tradeType = tradeType; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getBrokerage() { return brokerage; }
    public void setBrokerage(BigDecimal brokerage) { this.brokerage = brokerage; }
    public BigDecimal getStt() { return stt; }
    public void setStt(BigDecimal stt) { this.stt = stt; }
    public BigDecimal getExchangeCharges() { return exchangeCharges; }
    public void setExchangeCharges(BigDecimal exchangeCharges) { this.exchangeCharges = exchangeCharges; }
    public BigDecimal getGst() { return gst; }
    public void setGst(BigDecimal gst) { this.gst = gst; }
    public BigDecimal getSebiCharges() { return sebiCharges; }
    public void setSebiCharges(BigDecimal sebiCharges) { this.sebiCharges = sebiCharges; }
    public BigDecimal getStampDuty() { return stampDuty; }
    public void setStampDuty(BigDecimal stampDuty) { this.stampDuty = stampDuty; }
    public BigDecimal getTotalCharges() { return totalCharges; }
    public void setTotalCharges(BigDecimal totalCharges) { this.totalCharges = totalCharges; }
    public BigDecimal getNetAmount() { return netAmount; }
    public void setNetAmount(BigDecimal netAmount) { this.netAmount = netAmount; }
    public Instant getTradeDate() { return tradeDate; }
}
