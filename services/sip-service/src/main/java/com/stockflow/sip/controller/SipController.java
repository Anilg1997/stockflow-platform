package com.stockflow.sip.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.sip.model.Sip;
import com.stockflow.sip.service.SipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController @RequestMapping("/sip")
public class SipController {
    private final SipService service;
    public SipController(SipService service) { this.service = service; }
    @GetMapping public ResponseEntity<ApiResponse<List<Sip>>> getSips(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getUserSips(userId)));
    }
    @PostMapping public ResponseEntity<ApiResponse<Sip>> createSip(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam String schemeCode, @RequestParam BigDecimal amount,
            @RequestParam int sipDate, @RequestParam LocalDate startDate) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok("SIP created", service.createSip(userId, schemeCode, amount, sipDate, startDate)));
    }
    @PostMapping("/{sipId}/pause") public ResponseEntity<ApiResponse<Void>> pauseSip(@PathVariable UUID sipId) {
        service.pauseSip(sipId); return ResponseEntity.ok(ApiResponse.ok("SIP paused", null));
    }
    @PostMapping("/{sipId}/activate") public ResponseEntity<ApiResponse<Void>> activateSip(@PathVariable UUID sipId) {
        service.activateSip(sipId); return ResponseEntity.ok(ApiResponse.ok("SIP activated", null));
    }
}
