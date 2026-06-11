package com.stockflow.analytics.controller;

import com.stockflow.analytics.model.PortfolioPerformance;
import com.stockflow.analytics.service.AnalyticsService;
import com.stockflow.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    private final AnalyticsService service;
    public AnalyticsController(AnalyticsService service) { this.service = service; }
    @GetMapping("/performance")
    public ResponseEntity<ApiResponse<List<PortfolioPerformance>>> getPerformance(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getPerformance(userId)));
    }
    @GetMapping("/performance/latest")
    public ResponseEntity<ApiResponse<PortfolioPerformance>> getLatest(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getLatestPerformance(userId)));
    }
}
