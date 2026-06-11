package com.stockflow.user.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.user.dto.*;
import com.stockflow.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    // ── Profile ──────────────────────────────────────────────
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileDTO>> getProfile(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getProfile(userId)));
    }
    @PutMapping("/kyc")
    public ResponseEntity<ApiResponse<ProfileDTO>> updateKYC(@RequestHeader("X-User-Id") UUID userId, @Valid @RequestBody KYCRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("KYC submitted", userService.updateKYC(userId, request)));
    }

    // ── Bank Accounts ────────────────────────────────────────
    @GetMapping("/bank-accounts")
    public ResponseEntity<ApiResponse<List<BankAccountDTO>>> getBankAccounts(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getBankAccounts(userId)));
    }
    @PostMapping("/bank-accounts")
    public ResponseEntity<ApiResponse<BankAccountDTO>> addBankAccount(@RequestHeader("X-User-Id") UUID userId, @Valid @RequestBody BankAccountDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Bank account added", userService.addBankAccount(userId, dto)));
    }
    @DeleteMapping("/bank-accounts/{accountId}")
    public ResponseEntity<ApiResponse<Void>> deleteBankAccount(@RequestHeader("X-User-Id") UUID userId, @PathVariable UUID accountId) {
        userService.deleteBankAccount(userId, accountId);
        return ResponseEntity.ok(ApiResponse.ok("Bank account removed", null));
    }

    // ── Nominees ─────────────────────────────────────────────
    @GetMapping("/nominees")
    public ResponseEntity<ApiResponse<List<NomineeDTO>>> getNominees(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getNominees(userId)));
    }
    @PostMapping("/nominees")
    public ResponseEntity<ApiResponse<NomineeDTO>> addNominee(@RequestHeader("X-User-Id") UUID userId, @Valid @RequestBody NomineeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Nominee added", userService.addNominee(userId, dto)));
    }
    @DeleteMapping("/nominees/{nomineeId}")
    public ResponseEntity<ApiResponse<Void>> deleteNominee(@RequestHeader("X-User-Id") UUID userId, @PathVariable UUID nomineeId) {
        userService.deleteNominee(userId, nomineeId);
        return ResponseEntity.ok(ApiResponse.ok("Nominee removed", null));
    }
}
