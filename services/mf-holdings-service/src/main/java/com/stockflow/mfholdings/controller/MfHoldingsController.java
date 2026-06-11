package com.stockflow.mfholdings.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.mfholdings.model.MfHolding;
import com.stockflow.mfholdings.service.MfHoldingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController @RequestMapping("/mf-holdings")
public class MfHoldingsController {
    private final MfHoldingsService service;
    public MfHoldingsController(MfHoldingsService service) { this.service = service; }
    @GetMapping
    public ResponseEntity<ApiResponse<List<MfHolding>>> getHoldings(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getHoldings(userId)));
    }
}
