package com.stockflow.mf.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.mf.model.MutualFund;
import com.stockflow.mf.service.MfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/mf")
public class MfController {
    private final MfService service;
    public MfController(MfService service) { this.service = service; }
    @GetMapping public ResponseEntity<ApiResponse<List<MutualFund>>> getAll() { return ResponseEntity.ok(ApiResponse.ok(service.getAllFunds())); }
    @GetMapping("/category/{category}") public ResponseEntity<ApiResponse<List<MutualFund>>> getByCategory(@PathVariable String category) { return ResponseEntity.ok(ApiResponse.ok(service.getByCategory(category))); }
    @GetMapping("/search") public ResponseEntity<ApiResponse<List<MutualFund>>> search(@RequestParam String q) { return ResponseEntity.ok(ApiResponse.ok(service.search(q))); }
}
