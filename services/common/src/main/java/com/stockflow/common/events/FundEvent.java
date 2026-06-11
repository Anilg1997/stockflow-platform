package com.stockflow.common.events;

import java.math.BigDecimal;
import java.util.UUID;

public class FundEvent extends BaseEvent {

    private UUID userId;
    private FundAction action;
    private BigDecimal amount;
    private String reference;
    private BigDecimal availableBalance;

    public enum FundAction {
        DEPOSITED, WITHDRAWN, ORDER_LOCKED, ORDER_UNLOCKED
    }

    public FundEvent() {}

    // ── Getters & Setters ────────────────────────────────────
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public FundAction getAction() { return action; }
    public void setAction(FundAction action) { this.action = action; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public BigDecimal getAvailableBalance() { return availableBalance; }
    public void setAvailableBalance(BigDecimal availableBalance) { this.availableBalance = availableBalance; }
}
