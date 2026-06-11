package com.stockflow.holdings.service;

import com.stockflow.holdings.dto.*;
import com.stockflow.holdings.model.*;
import com.stockflow.holdings.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HoldingsService {
    private final HoldingRepository holdingRepo;
    private final RealizedPnlRepository pnlRepo;

    public HoldingsService(HoldingRepository holdingRepo, RealizedPnlRepository pnlRepo) {
        this.holdingRepo = holdingRepo;
        this.pnlRepo = pnlRepo;
    }

    public HoldingSummaryDTO getHoldingsSummary(UUID userId) {
        List<Holding> holdings = holdingRepo.findByUserId(userId);
        List<HoldingDTO> dtos = holdings.stream().map(this::toDTO).toList();

        HoldingSummaryDTO summary = new HoldingSummaryDTO();
        summary.setTotalHoldings(dtos.size());
        summary.setTotalInvested(dtos.stream()
            .map(HoldingDTO::getInvestedAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        summary.setTotalCurrentValue(dtos.stream()
            .map(HoldingDTO::getCurrentValue).reduce(BigDecimal.ZERO, BigDecimal::add));
        summary.setTotalPnl(summary.getTotalCurrentValue().subtract(summary.getTotalInvested()));
        if (summary.getTotalInvested().compareTo(BigDecimal.ZERO) > 0) {
            summary.setTotalPnlPercent(summary.getTotalPnl()
                .multiply(BigDecimal.valueOf(100))
                .divide(summary.getTotalInvested(), 2, RoundingMode.HALF_UP));
        }
        summary.setHoldings(dtos);
        return summary;
    }

    public List<HoldingDTO> getHoldings(UUID userId) {
        return holdingRepo.findByUserId(userId).stream().map(this::toDTO).toList();
    }

    public List<RealizedPnl> getRealizedPnl(UUID userId) {
        return pnlRepo.findByUserIdOrderByTradeDateDesc(userId);
    }

    public List<RealizedPnl> getRealizedPnlByFY(UUID userId, String financialYear) {
        return pnlRepo.findByUserIdAndFinancialYear(userId, financialYear);
    }

    private HoldingDTO toDTO(Holding h) {
        HoldingDTO dto = new HoldingDTO();
        dto.setId(h.getId());
        dto.setSymbol(h.getSymbol());
        dto.setQuantity(h.getQuantity());
        dto.setAvgBuyPrice(h.getAvgBuyPrice());
        dto.setInvestedAmount(h.getInvestedAmount());
        dto.setCurrentPrice(h.getAvgBuyPrice());
        dto.setCurrentValue(h.getAvgBuyPrice().multiply(BigDecimal.valueOf(h.getQuantity())));
        dto.setPnl(dto.getCurrentValue().subtract(h.getInvestedAmount()));
        if (h.getInvestedAmount().compareTo(BigDecimal.ZERO) > 0) {
            dto.setPnlPercent(dto.getPnl().multiply(BigDecimal.valueOf(100))
                .divide(h.getInvestedAmount(), 2, RoundingMode.HALF_UP));
        }
        return dto;
    }
}
