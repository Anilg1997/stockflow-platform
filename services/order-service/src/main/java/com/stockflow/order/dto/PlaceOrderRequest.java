package com.stockflow.order.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PlaceOrderRequest {
    @NotBlank private String symbol;
    @NotBlank private String orderType;
    @NotBlank @Pattern(regexp = "BUY|SELL") private String side;
    @NotBlank @Pattern(regexp = "INTRADAY|DELIVERY") private String tradeType;
    @Positive @Min(1) private int quantity;
    private BigDecimal price;
    private BigDecimal triggerPrice;
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
}
