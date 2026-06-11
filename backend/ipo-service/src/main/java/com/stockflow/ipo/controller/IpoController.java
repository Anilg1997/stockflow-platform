package com.stockflow.ipo.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.ipo.model.*;
import com.stockflow.ipo.service.IpoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController @RequestMapping("/ipo")
public class IpoController {
    private final IpoService service;
    public IpoController(IpoService service) { this.service = service; }
    @GetMapping public ResponseEntity<ApiResponse<List<Ipo>>> getAll() { return ResponseEntity.ok(ApiResponse.ok(service.getAllIpos())); }
    @GetMapping("/status/{status}") public ResponseEntity<ApiResponse<List<Ipo>>> getByStatus(@PathVariable String status) { return ResponseEntity.ok(ApiResponse.ok(service.getIposByStatus(status))); }
    @PostMapping("/apply") public ResponseEntity<ApiResponse<IpoApplication>> apply(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam UUID ipoId, @RequestParam int lots,
            @RequestParam BigDecimal bidPrice, @RequestParam(required = false) String upiId) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok("Application submitted", service.apply(userId, ipoId, lots, bidPrice, upiId)));
    }
    @GetMapping("/applications") public ResponseEntity<ApiResponse<List<IpoApplication>>> getApplications(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getUserApplications(userId)));
    }
}
