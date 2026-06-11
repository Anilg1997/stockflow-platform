package com.stockflow.ipo.model;

import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.time.LocalDate; import java.util.UUID;

@Entity @Table(schema = "ipo_schema", name = "ipos")
public class Ipo {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "company_name", length = 200) private String companyName;
    @Column(length = 20) private String symbol;
    @Column(name = "price_min") private BigDecimal priceMin;
    @Column(name = "price_max") private BigDecimal priceMax;
    @Column(name = "lot_size") private int lotSize;
    @Column(name = "open_date") private LocalDate openDate;
    @Column(name = "close_date") private LocalDate closeDate;
    @Column(name = "listing_date") private LocalDate listingDate;
    @Column(length = 20) private String status;
    private BigDecimal gmp;
    @Column(name = "subscription_times") private BigDecimal subscriptionTimes;
    @Column(name = "created_at") private Instant createdAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); }
    public UUID getId() { return id; } public void setId(UUID id) { this.id = id; }
    public String getCompanyName() { return companyName; } public void setCompanyName(String s) { companyName = s; }
    public String getSymbol() { return symbol; } public void setSymbol(String s) { this.symbol = s; }
    public BigDecimal getPriceMin() { return priceMin; } public void setPriceMin(BigDecimal p) { priceMin = p; }
    public BigDecimal getPriceMax() { return priceMax; } public void setPriceMax(BigDecimal p) { priceMax = p; }
    public int getLotSize() { return lotSize; } public void setLotSize(int l) { lotSize = l; }
    public LocalDate getOpenDate() { return openDate; } public void setOpenDate(LocalDate d) { openDate = d; }
    public LocalDate getCloseDate() { return closeDate; } public void setCloseDate(LocalDate d) { closeDate = d; }
    public LocalDate getListingDate() { return listingDate; } public void setListingDate(LocalDate d) { listingDate = d; }
    public String getStatus() { return status; } public void setStatus(String s) { status = s; }
    public BigDecimal getGmp() { return gmp; } public void setGmp(BigDecimal g) { gmp = g; }
    public BigDecimal getSubscriptionTimes() { return subscriptionTimes; } public void setSubscriptionTimes(BigDecimal s) { subscriptionTimes = s; }
}
