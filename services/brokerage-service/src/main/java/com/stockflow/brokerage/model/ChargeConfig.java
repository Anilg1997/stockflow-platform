package com.stockflow.brokerage.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(schema = "brokerage_schema", name = "charge_config")
public class ChargeConfig {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "charge_type", unique = true, nullable = false, length = 50)
    private String chargeType;
    @Column(nullable = false, precision = 8, scale = 6)
    private BigDecimal rate;
    @Column(name = "min_amount") private BigDecimal minAmount = BigDecimal.ZERO;
    @Column(name = "max_amount") private BigDecimal maxAmount;
    @Column(columnDefinition = "TEXT") private String description;
    @Column(name = "is_active") private boolean isActive = true;
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getChargeType() { return chargeType; }
    public void setChargeType(String chargeType) { this.chargeType = chargeType; }
    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }
    public BigDecimal getMinAmount() { return minAmount; }
    public void setMinAmount(BigDecimal minAmount) { this.minAmount = minAmount; }
    public BigDecimal getMaxAmount() { return maxAmount; }
    public void setMaxAmount(BigDecimal maxAmount) { this.maxAmount = maxAmount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
