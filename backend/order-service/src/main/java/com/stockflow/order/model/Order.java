package com.stockflow.order.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "order_schema", name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(nullable = false, length = 20)
    private String symbol;
    @Column(name = "order_type", nullable = false, length = 20)
    private String orderType;
    @Column(nullable = false, length = 4)
    private String side;
    @Column(name = "trade_type", nullable = false, length = 10)
    private String tradeType;
    @Column(nullable = false)
    private int quantity;
    private BigDecimal price;
    @Column(name = "trigger_price")
    private BigDecimal triggerPrice;
    @Column(length = 20)
    private String status = "OPEN";
    @Column(name = "executed_price")
    private BigDecimal executedPrice;
    @Column(name = "executed_quantity")
    private int executedQuantity;
    @Column(name = "placed_at")
    private Instant placedAt;
    @Column(name = "executed_at")
    private Instant executedAt;
    @Column(name = "cancelled_at")
    private Instant cancelledAt;
    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;
    private int version = 0;
    @PrePersist
    protected void onCreate() { placedAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }
    public String getSide() { return side; }
    public void setSide(String side) { this.side = side; }
    public String getTradeType() { return tradeType; }
    public void setTradeType(String tradeType) { this.tradeType = tradeType; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getTriggerPrice() { return triggerPrice; }
    public void setTriggerPrice(BigDecimal triggerPrice) { this.triggerPrice = triggerPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getExecutedPrice() { return executedPrice; }
    public void setExecutedPrice(BigDecimal executedPrice) { this.executedPrice = executedPrice; }
    public int getExecutedQuantity() { return executedQuantity; }
    public void setExecutedQuantity(int executedQuantity) { this.executedQuantity = executedQuantity; }
    public Instant getPlacedAt() { return placedAt; }
    public Instant getExecutedAt() { return executedAt; }
    public void setExecutedAt(Instant executedAt) { this.executedAt = executedAt; }
    public Instant getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(Instant cancelledAt) { this.cancelledAt = cancelledAt; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
}
