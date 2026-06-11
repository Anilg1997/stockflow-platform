package com.stockflow.watchlist.repository;

import com.stockflow.watchlist.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WatchlistRepository extends JpaRepository<Watchlist, UUID> {
    List<Watchlist> findByUserIdOrderByCreatedAtDesc(UUID userId);
    Optional<Watchlist> findByUserIdAndIsDefaultTrue(UUID userId);
}
