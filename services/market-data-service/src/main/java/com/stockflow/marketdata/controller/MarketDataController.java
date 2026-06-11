package com.stockflow.marketdata.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.marketdata.dto.*;
import com.stockflow.marketdata.service.MarketDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/market")
public class MarketDataController {
    private final MarketDataService marketService;
    public MarketDataController(MarketDataService marketService) { this.marketService = marketService; }

    @GetMapping("/stocks")
    public ResponseEntity<ApiResponse<List<StockDTO>>> getAllStocks() {
        return ResponseEntity.ok(ApiResponse.ok(marketService.getAllStocks()));
    }

    @GetMapping("/stocks/{symbol}")
    public ResponseEntity<ApiResponse<StockDTO>> getStock(@PathVariable String symbol) {
        return ResponseEntity.ok(ApiResponse.ok(marketService.getStockBySymbol(symbol)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StockDTO>>> searchStocks(@RequestParam String q) {
        return ResponseEntity.ok(ApiResponse.ok(marketService.searchStocks(q)));
    }

    @GetMapping("/sectors")
    public ResponseEntity<ApiResponse<List<String>>> getSectors() {
        return ResponseEntity.ok(ApiResponse.ok(marketService.getAllSectors()));
    }

    @GetMapping("/sectors/{sector}")
    public ResponseEntity<ApiResponse<List<StockDTO>>> getStocksBySector(@PathVariable String sector) {
        return ResponseEntity.ok(ApiResponse.ok(marketService.getStocksBySector(sector)));
    }

    @GetMapping("/indices")
    public ResponseEntity<ApiResponse<List<IndexDTO>>> getIndices() {
        return ResponseEntity.ok(ApiResponse.ok(marketService.getAllIndices()));
    }
}
