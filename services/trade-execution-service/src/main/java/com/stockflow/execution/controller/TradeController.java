package com.stockflow.execution.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.execution.model.Trade;
import com.stockflow.execution.service.TradeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trades")
public class TradeController {
    private final TradeService service;
    public TradeController(TradeService service) { this.service = service; }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Trade>>> getTrades(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getUserTrades(userId)));
    }
    @GetMapping("/{tradeId}")
    public ResponseEntity<ApiResponse<Trade>> getTrade(@PathVariable UUID tradeId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getTrade(tradeId)));
    }
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<Page<Trade>>> getTradesPaged(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(service.getUserTradesPaged(userId, PageRequest.of(page, size))));
    }
}
