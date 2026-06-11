package com.stockflow.marketdata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockDTO {
    private UUID id;
    private String symbol, name, exchange, sector;
    private BigDecimal currentPrice, open, high, low, close;
    private BigDecimal change, changePercent, weekHigh52, weekLow52;
    private Long volume;
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
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
    public BigDecimal getOpen() { return open; }
    public void setOpen(BigDecimal open) { this.open = open; }
    public BigDecimal getHigh() { return high; }
    public void setHigh(BigDecimal high) { this.high = high; }
    public BigDecimal getLow() { return low; }
    public void setLow(BigDecimal low) { this.low = low; }
    public BigDecimal getClose() { return close; }
    public void setClose(BigDecimal close) { this.close = close; }
    public BigDecimal getChange() { return change; }
    public void setChange(BigDecimal change) { this.change = change; }
    public BigDecimal getChangePercent() { return changePercent; }
    public void setChangePercent(BigDecimal changePercent) { this.changePercent = changePercent; }
    public BigDecimal getWeekHigh52() { return weekHigh52; }
    public void setWeekHigh52(BigDecimal weekHigh52) { this.weekHigh52 = weekHigh52; }
    public BigDecimal getWeekLow52() { return weekLow52; }
    public void setWeekLow52(BigDecimal weekLow52) { this.weekLow52 = weekLow52; }
    public Long getVolume() { return volume; }
    public void setVolume(Long volume) { this.volume = volume; }
}
