package com.stockflow.funds.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class FundRequest {
    @NotNull @DecimalMin(value = "1.00", message = "Amount must be at least 1")
    private BigDecimal amount;
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
