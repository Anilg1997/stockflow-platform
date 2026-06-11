package com.stockflow.news.repository;

import com.stockflow.news.model.NewsArticle;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NewsRepository extends MongoRepository<NewsArticle, String> {
    List<NewsArticle> findBySymbolsContainingOrderByPublishedAtDesc(String symbol);
    List<NewsArticle> findByFeaturedTrueOrderByPublishedAtDesc();
    List<NewsArticle> findByOrderByPublishedAtDesc();
}
