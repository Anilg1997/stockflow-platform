package com.stockflow.news.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.List;

@Document(collection = "news_articles")
public class NewsArticle {
    @Id
    private String id;
    private String title;
    private String summary;
    private String content;
    private String source;
    private String author;
    private List<String> symbols;
    private List<String> tags;
    private String sentiment;
    private String imageUrl;
    private Instant publishedAt = Instant.now();
    private boolean featured;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public List<String> getSymbols() { return symbols; }
    public void setSymbols(List<String> symbols) { this.symbols = symbols; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }
}
