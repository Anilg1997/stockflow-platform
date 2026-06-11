package com.stockflow.brokerage.controller;

import com.stockflow.brokerage.model.ChargeConfig;
import com.stockflow.brokerage.service.BrokerageService;
import com.stockflow.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/brokerage")
public class BrokerageController {
    private final BrokerageService service;
    public BrokerageController(BrokerageService service) { this.service = service; }
    @GetMapping("/charges")
    public ResponseEntity<ApiResponse<List<ChargeConfig>>> getCharges() {
        return ResponseEntity.ok(ApiResponse.ok(service.getAllCharges()));
    }
    @GetMapping("/estimate")
    public ResponseEntity<ApiResponse<BrokerageService.BrokerageEstimate>> estimate(
            @RequestParam BigDecimal price,
            @RequestParam int quantity,
            @RequestParam String side) {
        return ResponseEntity.ok(ApiResponse.ok(service.calculateCharges(price, quantity, side)));
    }
}
