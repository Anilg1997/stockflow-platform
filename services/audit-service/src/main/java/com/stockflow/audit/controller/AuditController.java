package com.stockflow.audit.controller;

import com.stockflow.audit.model.AuditLog;
import com.stockflow.audit.service.AuditService;
import com.stockflow.common.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditController {
    private final AuditService service;
    public AuditController(AuditService service) { this.service = service; }
    @PostMapping("/log")
    public ResponseEntity<ApiResponse<AuditLog>> log(@RequestBody AuditLog log) {
        return ResponseEntity.ok(ApiResponse.ok(service.log(log)));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Page<AuditLog>>> getUserLogs(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(service.getUserAuditLogs(userId, PageRequest.of(page, size))));
    }
    @GetMapping("/action/{action}")
    public ResponseEntity<ApiResponse<Page<AuditLog>>> getByAction(
            @PathVariable String action,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(service.getByAction(action, PageRequest.of(page, size))));
    }
}
