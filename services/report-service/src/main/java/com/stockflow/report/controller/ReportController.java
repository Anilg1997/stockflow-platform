package com.stockflow.report.controller;

import com.stockflow.report.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService service;
    public ReportController(ReportService service) { this.service = service; }

    @GetMapping("/holdings")
    public ResponseEntity<byte[]> downloadHoldings(@RequestHeader("X-User-Id") UUID userId) {
        byte[] data = service.generateHoldingReport(userId);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=holdings.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(data);
    }

    @GetMapping("/tax/{financialYear}")
    public ResponseEntity<byte[]> downloadTaxReport(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable String financialYear) {
        byte[] data = service.generateTaxReport(userId, financialYear);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tax-report.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(data);
    }

    @GetMapping("/transactions")
    public ResponseEntity<byte[]> downloadTransactions(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        byte[] data = service.generateTransactionReport(userId, from, to);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(data);
    }
}
