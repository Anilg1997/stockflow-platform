package com.stockflow.watchlist.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "watchlist_schema", name = "watchlists")
public class Watchlist {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(name = "is_default")
    private boolean isDefault;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @OneToMany(mappedBy = "watchlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WatchlistItem> items = new ArrayList<>();
    @PrePersist
    protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate
    protected void onUpdate() { updatedAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean aDefault) { isDefault = aDefault; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public List<WatchlistItem> getItems() { return items; }
    public void setItems(List<WatchlistItem> items) { this.items = items; }
}
