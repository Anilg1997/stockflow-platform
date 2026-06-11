package com.stockflow.funds.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletDTO {
    private UUID id;
    private UUID userId;
    private BigDecimal availableBalance;
    private BigDecimal onHold;
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public BigDecimal getAvailableBalance() { return availableBalance; }
    public void setAvailableBalance(BigDecimal availableBalance) { this.availableBalance = availableBalance; }
    public BigDecimal getOnHold() { return onHold; }
    public void setOnHold(BigDecimal onHold) { this.onHold = onHold; }
}
