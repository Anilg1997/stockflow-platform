package com.stockflow.news.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.news.model.NewsArticle;
import com.stockflow.news.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService service;
    public NewsController(NewsService service) { this.service = service; }
    @GetMapping
    public ResponseEntity<ApiResponse<List<NewsArticle>>> getAllNews() {
        return ResponseEntity.ok(ApiResponse.ok(service.getAllNews()));
    }
    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<NewsArticle>>> getFeatured() {
        return ResponseEntity.ok(ApiResponse.ok(service.getFeaturedNews()));
    }
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<ApiResponse<List<NewsArticle>>> getBySymbol(@PathVariable String symbol) {
        return ResponseEntity.ok(ApiResponse.ok(service.getNewsBySymbol(symbol)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NewsArticle>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(service.getNewsById(id)));
    }
}
