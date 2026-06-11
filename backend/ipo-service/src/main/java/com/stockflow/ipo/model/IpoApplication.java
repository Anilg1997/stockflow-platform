package com.stockflow.ipo.model;

import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;

@Entity @Table(schema = "ipo_schema", name = "ipo_applications")
public class IpoApplication {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "user_id", nullable = false) private UUID userId;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ipo_id", nullable = false) private Ipo ipo;
    @Column(nullable = false) private int lots;
    @Column(name = "bid_price", nullable = false) private BigDecimal bidPrice;
    @Column(length = 20) private String status = "APPLIED";
    @Column(name = "upi_id", length = 50) private String upiId;
    @Column(name = "applied_at") private Instant appliedAt;
    @Column(name = "allotment_result", length = 20) private String allotmentResult;
    @PrePersist protected void onCreate() { appliedAt = Instant.now(); }
    public UUID getId() { return id; } public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; } public void setUserId(UUID u) { userId = u; }
    public Ipo getIpo() { return ipo; } public void setIpo(Ipo i) { ipo = i; }
    public int getLots() { return lots; } public void setLots(int l) { lots = l; }
    public BigDecimal getBidPrice() { return bidPrice; } public void setBidPrice(BigDecimal p) { bidPrice = p; }
    public String getStatus() { return status; } public void setStatus(String s) { status = s; }
    public String getUpiId() { return upiId; } public void setUpiId(String u) { upiId = u; }
    public Instant getAppliedAt() { return appliedAt; }
    public String getAllotmentResult() { return allotmentResult; } public void setAllotmentResult(String s) { allotmentResult = s; }
}
