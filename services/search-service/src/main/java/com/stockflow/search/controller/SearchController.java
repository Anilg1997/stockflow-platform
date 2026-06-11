package com.stockflow.search.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.search.model.SearchResult;
import com.stockflow.search.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService service;
    public SearchController(SearchService service) { this.service = service; }
    @GetMapping
    public ResponseEntity<ApiResponse<List<SearchResult>>> search(@RequestParam String q) {
        return ResponseEntity.ok(ApiResponse.ok(service.search(q)));
    }
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<SearchResult>>> searchByType(
            @RequestParam String q, @PathVariable String type) {
        return ResponseEntity.ok(ApiResponse.ok(service.searchByType(q, type)));
    }
}
