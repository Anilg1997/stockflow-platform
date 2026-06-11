package com.stockflow.brokerage.service;

import com.stockflow.brokerage.model.ChargeConfig;
import com.stockflow.brokerage.repository.ChargeConfigRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class BrokerageService {
    private final ChargeConfigRepository repo;
    public BrokerageService(ChargeConfigRepository repo) { this.repo = repo; }

    public List<ChargeConfig> getAllCharges() {
        return repo.findAll();
    }

    public BrokerageEstimate calculateCharges(BigDecimal price, int quantity, String side) {
        BigDecimal turnover = price.multiply(BigDecimal.valueOf(quantity));
        BrokerageEstimate estimate = new BrokerageEstimate();
        estimate.setTurnover(turnover);
        estimate.setQuantity(quantity);
        estimate.setPrice(price);

        repo.findByChargeTypeAndIsActiveTrue("BROKERAGE").ifPresent(c -> {
            BigDecimal rawCharge = turnover.multiply(c.getRate()).min(c.getMaxAmount() != null ? c.getMaxAmount() : turnover);
            BigDecimal charge = rawCharge.compareTo(c.getMinAmount()) < 0 ? c.getMinAmount() : rawCharge;
            estimate.setBrokerage(charge);
        });

        if ("SELL".equals(side)) {
            repo.findByChargeTypeAndIsActiveTrue("STT").ifPresent(c ->
                estimate.setStt(turnover.multiply(c.getRate()).setScale(2, RoundingMode.HALF_UP)));
        }

        repo.findByChargeTypeAndIsActiveTrue("EXCHANGE_CHARGES").ifPresent(c ->
            estimate.setExchangeCharges(turnover.multiply(c.getRate()).setScale(2, RoundingMode.HALF_UP)));
        repo.findByChargeTypeAndIsActiveTrue("SEBI_CHARGES").ifPresent(c ->
            estimate.setSebiCharges(turnover.multiply(c.getRate()).setScale(2, RoundingMode.HALF_UP)));
        repo.findByChargeTypeAndIsActiveTrue("STAMP_DUTY").ifPresent(c -> {
            if ("BUY".equals(side))
                estimate.setStampDuty(turnover.multiply(c.getRate()).setScale(2, RoundingMode.HALF_UP));
        });

        BigDecimal subTotal = BigDecimal.ZERO;
        if (estimate.getBrokerage() != null) subTotal = subTotal.add(estimate.getBrokerage());
        if (estimate.getExchangeCharges() != null) subTotal = subTotal.add(estimate.getExchangeCharges());
        if (estimate.getSebiCharges() != null) subTotal = subTotal.add(estimate.getSebiCharges());
        final BigDecimal finalSubTotal = subTotal;
        repo.findByChargeTypeAndIsActiveTrue("GST").ifPresent(c ->
            estimate.setGst(finalSubTotal.multiply(c.getRate()).setScale(2, RoundingMode.HALF_UP)));

        BigDecimal total = BigDecimal.ZERO;
        if (estimate.getBrokerage() != null) total = total.add(estimate.getBrokerage());
        if (estimate.getStt() != null) total = total.add(estimate.getStt());
        if (estimate.getExchangeCharges() != null) total = total.add(estimate.getExchangeCharges());
        if (estimate.getGst() != null) total = total.add(estimate.getGst());
        if (estimate.getSebiCharges() != null) total = total.add(estimate.getSebiCharges());
        if (estimate.getStampDuty() != null) total = total.add(estimate.getStampDuty());
        estimate.setTotalCharges(total);
        estimate.setNetAmount("BUY".equals(side) ? turnover.add(total) : turnover.subtract(total));
        return estimate;
    }

    public static class BrokerageEstimate {
        private BigDecimal turnover;
        private int quantity;
        private BigDecimal price;
        private BigDecimal brokerage = BigDecimal.ZERO;
        private BigDecimal stt = BigDecimal.ZERO;
        private BigDecimal exchangeCharges = BigDecimal.ZERO;
        private BigDecimal gst = BigDecimal.ZERO;
        private BigDecimal sebiCharges = BigDecimal.ZERO;
        private BigDecimal stampDuty = BigDecimal.ZERO;
        private BigDecimal totalCharges = BigDecimal.ZERO;
        private BigDecimal netAmount;
        public BigDecimal getTurnover() { return turnover; }
        public void setTurnover(BigDecimal turnover) { this.turnover = turnover; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        public BigDecimal getBrokerage() { return brokerage; }
        public void setBrokerage(BigDecimal brokerage) { this.brokerage = brokerage; }
        public BigDecimal getStt() { return stt; }
        public void setStt(BigDecimal stt) { this.stt = stt; }
        public BigDecimal getExchangeCharges() { return exchangeCharges; }
        public void setExchangeCharges(BigDecimal exchangeCharges) { this.exchangeCharges = exchangeCharges; }
        public BigDecimal getGst() { return gst; }
        public void setGst(BigDecimal gst) { this.gst = gst; }
        public BigDecimal getSebiCharges() { return sebiCharges; }
        public void setSebiCharges(BigDecimal sebiCharges) { this.sebiCharges = sebiCharges; }
        public BigDecimal getStampDuty() { return stampDuty; }
        public void setStampDuty(BigDecimal stampDuty) { this.stampDuty = stampDuty; }
        public BigDecimal getTotalCharges() { return totalCharges; }
        public void setTotalCharges(BigDecimal totalCharges) { this.totalCharges = totalCharges; }
        public BigDecimal getNetAmount() { return netAmount; }
        public void setNetAmount(BigDecimal netAmount) { this.netAmount = netAmount; }
    }
}
