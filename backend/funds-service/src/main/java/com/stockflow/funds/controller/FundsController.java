package com.stockflow.funds.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.funds.dto.*;
import com.stockflow.funds.model.FundTransaction;
import com.stockflow.funds.service.FundsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funds")
public class FundsController {
    private final FundsService fundsService;
    public FundsController(FundsService fundsService) { this.fundsService = fundsService; }

    @GetMapping("/wallet")
    public ResponseEntity<ApiResponse<WalletDTO>> getWallet(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(fundsService.getWallet(userId)));
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<WalletDTO>> deposit(
            @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody FundRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Deposit successful", fundsService.deposit(userId, request.getAmount())));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<WalletDTO>> withdraw(
            @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody FundRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Withdrawal successful", fundsService.withdraw(userId, request.getAmount())));
    }

    @GetMapping("/transactions")
    public ResponseEntity<ApiResponse<List<FundTransaction>>> getTransactions(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(fundsService.getTransactions(userId)));
    }

    @GetMapping("/transactions/paged")
    public ResponseEntity<ApiResponse<Page<FundTransaction>>> getTransactionsPaged(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(fundsService.getTransactionsPaged(userId, PageRequest.of(page, size))));
    }
}
