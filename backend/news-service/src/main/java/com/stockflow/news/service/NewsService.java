package com.stockflow.news.service;

import com.stockflow.news.model.NewsArticle;
import com.stockflow.news.repository.NewsRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NewsService {
    private final NewsRepository repo;
    public NewsService(NewsRepository repo) { this.repo = repo; }
    public List<NewsArticle> getAllNews() {
        return repo.findByOrderByPublishedAtDesc();
    }
    public List<NewsArticle> getNewsBySymbol(String symbol) {
        return repo.findBySymbolsContainingOrderByPublishedAtDesc(symbol.toUpperCase());
    }
    public List<NewsArticle> getFeaturedNews() {
        return repo.findByFeaturedTrueOrderByPublishedAtDesc();
    }
    public NewsArticle getNewsById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("News not found"));
    }
}
