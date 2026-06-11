package com.stockflow.sip.model;

import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.time.LocalDate; import java.util.UUID;

@Entity @Table(schema = "mf_schema", name = "sips")
public class Sip {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "user_id", nullable = false) private UUID userId;
    @Column(name = "scheme_code", nullable = false, length = 20) private String schemeCode;
    @Column(name = "monthly_amount", nullable = false) private BigDecimal monthlyAmount;
    @Column(name = "sip_date", nullable = false) private int sipDate;
    @Column(name = "start_date", nullable = false) private LocalDate startDate;
    @Column(name = "end_date") private LocalDate endDate;
    @Column(length = 20) private String status = "ACTIVE";
    @Column(name = "next_execution") private LocalDate nextExecution;
    @Column(name = "created_at") private Instant createdAt;
    @Column(name = "updated_at") private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }
    public UUID getId() { return id; } public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; } public void setUserId(UUID u) { userId = u; }
    public String getSchemeCode() { return schemeCode; } public void setSchemeCode(String s) { schemeCode = s; }
    public BigDecimal getMonthlyAmount() { return monthlyAmount; } public void setMonthlyAmount(BigDecimal a) { monthlyAmount = a; }
    public int getSipDate() { return sipDate; } public void setSipDate(int d) { sipDate = d; }
    public LocalDate getStartDate() { return startDate; } public void setStartDate(LocalDate d) { startDate = d; }
    public LocalDate getEndDate() { return endDate; } public void setEndDate(LocalDate d) { endDate = d; }
    public String getStatus() { return status; } public void setStatus(String s) { status = s; }
    public LocalDate getNextExecution() { return nextExecution; } public void setNextExecution(LocalDate d) { nextExecution = d; }
}
