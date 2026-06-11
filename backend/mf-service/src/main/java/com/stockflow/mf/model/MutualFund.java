package com.stockflow.mf.model;

import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;

@Entity @Table(schema = "mf_schema", name = "mutual_funds")
public class MutualFund {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "scheme_code", unique = true, length = 20) private String schemeCode;
    @Column(name = "scheme_name", length = 300) private String schemeName;
    @Column(name = "fund_house", length = 100) private String fundHouse;
    @Column(length = 50) private String category;
    @Column(name = "sub_category", length = 50) private String subCategory;
    private java.math.BigDecimal nav;
    @Column(name = "day_change") private BigDecimal dayChange;
    @Column(name = "risk_level", length = 20) private String riskLevel;
    @Column(name = "exit_load", length = 100) private String exitLoad;
    @Column(name = "expense_ratio") private BigDecimal expenseRatio;
    @Column(name = "is_active") private boolean isActive = true;
    @Column(name = "created_at") private Instant createdAt;
    @Column(name = "updated_at") private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }
    public UUID getId() { return id; } public void setId(UUID id) { this.id = id; }
    public String getSchemeCode() { return schemeCode; } public void setSchemeCode(String s) { schemeCode = s; }
    public String getSchemeName() { return schemeName; } public void setSchemeName(String s) { schemeName = s; }
    public String getFundHouse() { return fundHouse; } public void setFundHouse(String s) { fundHouse = s; }
    public String getCategory() { return category; } public void setCategory(String s) { category = s; }
    public String getSubCategory() { return subCategory; } public void setSubCategory(String s) { subCategory = s; }
    public BigDecimal getNav() { return nav; } public void setNav(BigDecimal n) { nav = n; }
    public BigDecimal getDayChange() { return dayChange; } public void setDayChange(BigDecimal d) { dayChange = d; }
    public String getRiskLevel() { return riskLevel; } public void setRiskLevel(String s) { riskLevel = s; }
    public String getExitLoad() { return exitLoad; } public void setExitLoad(String s) { exitLoad = s; }
    public BigDecimal getExpenseRatio() { return expenseRatio; } public void setExpenseRatio(BigDecimal e) { expenseRatio = e; }
    public boolean isActive() { return isActive; } public void setActive(boolean a) { isActive = a; }
    public Instant getCreatedAt() { return createdAt; }
}
