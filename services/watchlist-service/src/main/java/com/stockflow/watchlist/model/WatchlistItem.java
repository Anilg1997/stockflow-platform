package com.stockflow.watchlist.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "watchlist_schema", name = "watchlist_items",
       uniqueConstraints = @UniqueConstraint(columnNames = {"watchlist_id", "symbol"}))
public class WatchlistItem {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "watchlist_id", nullable = false)
    private Watchlist watchlist;
    @Column(nullable = false, length = 20)
    private String symbol;
    @Column(name = "added_at")
    private Instant addedAt;
    @PrePersist
    protected void onCreate() { addedAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Watchlist getWatchlist() { return watchlist; }
    public void setWatchlist(Watchlist watchlist) { this.watchlist = watchlist; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public Instant getAddedAt() { return addedAt; }
}
