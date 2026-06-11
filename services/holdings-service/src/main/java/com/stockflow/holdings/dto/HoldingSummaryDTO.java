package com.stockflow.holdings.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HoldingSummaryDTO {
    private int totalHoldings;
    private BigDecimal totalInvested;
    private BigDecimal totalCurrentValue;
    private BigDecimal totalPnl;
    private BigDecimal totalPnlPercent;
    private List<HoldingDTO> holdings;
    public int getTotalHoldings() { return totalHoldings; }
    public void setTotalHoldings(int totalHoldings) { this.totalHoldings = totalHoldings; }
    public BigDecimal getTotalInvested() { return totalInvested; }
    public void setTotalInvested(BigDecimal totalInvested) { this.totalInvested = totalInvested; }
    public BigDecimal getTotalCurrentValue() { return totalCurrentValue; }
    public void setTotalCurrentValue(BigDecimal totalCurrentValue) { this.totalCurrentValue = totalCurrentValue; }
    public BigDecimal getTotalPnl() { return totalPnl; }
    public void setTotalPnl(BigDecimal totalPnl) { this.totalPnl = totalPnl; }
    public BigDecimal getTotalPnlPercent() { return totalPnlPercent; }
    public void setTotalPnlPercent(BigDecimal totalPnlPercent) { this.totalPnlPercent = totalPnlPercent; }
    public List<HoldingDTO> getHoldings() { return holdings; }
    public void setHoldings(List<HoldingDTO> holdings) { this.holdings = holdings; }
}
