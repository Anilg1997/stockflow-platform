package com.stockflow.alert.controller;

import com.stockflow.alert.model.PriceAlert;
import com.stockflow.alert.service.AlertService;
import com.stockflow.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alerts")
public class AlertController {
    private final AlertService service;
    public AlertController(AlertService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PriceAlert>>> getAlerts(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getUserAlerts(userId)));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<PriceAlert>>> getActive(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getActiveAlerts(userId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PriceAlert>> createAlert(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam String symbol,
            @RequestParam BigDecimal targetPrice,
            @RequestParam(defaultValue = "ABOVE") String condition) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok("Alert created", service.createAlert(userId, symbol, targetPrice, condition)));
    }

    @DeleteMapping("/{alertId}")
    public ResponseEntity<ApiResponse<Void>> deleteAlert(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID alertId) {
        service.deleteAlert(userId, alertId);
        return ResponseEntity.ok(ApiResponse.ok("Alert deleted", null));
    }
}
