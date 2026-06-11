package com.stockflow.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private UUID id;
    private UUID userId;

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotBlank(message = "Order type is required")
    private String orderType;

    @NotBlank(message = "Side is required")
    private String side;

    @NotBlank(message = "Trade type is required")
    private String tradeType;

    @Positive(message = "Quantity must be positive")
    private int quantity;

    private BigDecimal price;
    private BigDecimal triggerPrice;
    private String status;
    private BigDecimal executedPrice;
    private int executedQuantity;
    private Instant placedAt;
    private Instant executedAt;
    private String rejectionReason;

    // ── Getters & Setters ────────────────────────────────────
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
    public void setPlacedAt(Instant placedAt) { this.placedAt = placedAt; }

    public Instant getExecutedAt() { return executedAt; }
    public void setExecutedAt(Instant executedAt) { this.executedAt = executedAt; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}
