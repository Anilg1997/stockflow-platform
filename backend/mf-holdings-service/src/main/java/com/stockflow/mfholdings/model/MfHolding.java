package com.stockflow.mfholdings.model;

import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;

@Entity @Table(schema = "mf_schema", name = "mf_holdings",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "scheme_code"}))
public class MfHolding {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "user_id", nullable = false) private UUID userId;
    @Column(name = "scheme_code", nullable = false, length = 20) private String schemeCode;
    @Column(nullable = false) private BigDecimal units;
    @Column(name = "avg_nav", nullable = false) private BigDecimal avgNav;
    @Column(name = "invested_amount", nullable = false) private BigDecimal investedAmount;
    @Column(name = "last_updated") private Instant lastUpdated;
    @PrePersist @PreUpdate protected void onUpdate() { lastUpdated = Instant.now(); }
    public UUID getId() { return id; } public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; } public void setUserId(UUID u) { userId = u; }
    public String getSchemeCode() { return schemeCode; } public void setSchemeCode(String s) { schemeCode = s; }
    public BigDecimal getUnits() { return units; } public void setUnits(BigDecimal u) { units = u; }
    public BigDecimal getAvgNav() { return avgNav; } public void setAvgNav(BigDecimal a) { avgNav = a; }
    public BigDecimal getInvestedAmount() { return investedAmount; } public void setInvestedAmount(BigDecimal a) { investedAmount = a; }
}
