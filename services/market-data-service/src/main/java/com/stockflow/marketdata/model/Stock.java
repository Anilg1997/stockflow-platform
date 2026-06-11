package com.stockflow.marketdata.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "market_schema", name = "stocks")
public class Stock {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false, length = 20)
    private String symbol;
    @Column(nullable = false, length = 200)
    private String name;
    @Column(nullable = false, length = 10)
    private String exchange = "NSE";
    @Column(nullable = false, length = 50)
    private String sector;
    @Column(length = 12)
    private String isin;
    @Column(name = "face_value")
    private BigDecimal faceValue = BigDecimal.TEN;
    @Column(name = "is_active")
    private boolean isActive = true;
    @Column(name = "created_at")
    private Instant createdAt;
    @PrePersist
    protected void onCreate() { createdAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getExchange() { return exchange; }
    public void setExchange(String exchange) { this.exchange = exchange; }
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    public String getIsin() { return isin; }
    public void setIsin(String isin) { this.isin = isin; }
    public BigDecimal getFaceValue() { return faceValue; }
    public void setFaceValue(BigDecimal faceValue) { this.faceValue = faceValue; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public Instant getCreatedAt() { return createdAt; }
}
