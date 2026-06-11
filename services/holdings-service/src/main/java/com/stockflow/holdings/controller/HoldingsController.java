package com.stockflow.holdings.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.holdings.dto.*;
import com.stockflow.holdings.model.RealizedPnl;
import com.stockflow.holdings.service.HoldingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/holdings")
public class HoldingsController {
    private final HoldingsService holdingsService;
    public HoldingsController(HoldingsService holdingsService) { this.holdingsService = holdingsService; }

    @GetMapping
    public ResponseEntity<ApiResponse<HoldingSummaryDTO>> getHoldings(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(holdingsService.getHoldingsSummary(userId)));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<HoldingDTO>>> getHoldingsList(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(holdingsService.getHoldings(userId)));
    }

    @GetMapping("/pnl")
    public ResponseEntity<ApiResponse<List<RealizedPnl>>> getRealizedPnl(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(holdingsService.getRealizedPnl(userId)));
    }

    @GetMapping("/pnl/{financialYear}")
    public ResponseEntity<ApiResponse<List<RealizedPnl>>> getPnlByFY(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable String financialYear) {
        return ResponseEntity.ok(ApiResponse.ok(holdingsService.getRealizedPnlByFY(userId, financialYear)));
    }
}
