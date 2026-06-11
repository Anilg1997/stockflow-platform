package com.stockflow.watchlist.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.watchlist.dto.*;
import com.stockflow.watchlist.service.WatchlistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {
    private final WatchlistService watchlistService;
    public WatchlistController(WatchlistService watchlistService) { this.watchlistService = watchlistService; }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WatchlistDTO>>> getWatchlists(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(watchlistService.getWatchlists(userId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WatchlistDTO>> createWatchlist(
            @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody CreateWatchlistRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok("Watchlist created", watchlistService.createWatchlist(userId, request)));
    }

    @PostMapping("/{watchlistId}/symbols/{symbol}")
    public ResponseEntity<ApiResponse<Void>> addSymbol(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID watchlistId,
            @PathVariable String symbol) {
        watchlistService.addSymbol(userId, watchlistId, symbol);
        return ResponseEntity.ok(ApiResponse.ok("Symbol added to watchlist", null));
    }

    @DeleteMapping("/{watchlistId}/symbols/{symbol}")
    public ResponseEntity<ApiResponse<Void>> removeSymbol(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID watchlistId,
            @PathVariable String symbol) {
        watchlistService.removeSymbol(userId, watchlistId, symbol);
        return ResponseEntity.ok(ApiResponse.ok("Symbol removed from watchlist", null));
    }

    @DeleteMapping("/{watchlistId}")
    public ResponseEntity<ApiResponse<Void>> deleteWatchlist(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID watchlistId) {
        watchlistService.deleteWatchlist(userId, watchlistId);
        return ResponseEntity.ok(ApiResponse.ok("Watchlist deleted", null));
    }
}
