package com.stockflow.watchlist.repository;

import com.stockflow.watchlist.model.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface WatchlistItemRepository extends JpaRepository<WatchlistItem, UUID> {
    Optional<WatchlistItem> findByWatchlistIdAndSymbol(UUID watchlistId, String symbol);
    void deleteByWatchlistIdAndSymbol(UUID watchlistId, String symbol);
}
