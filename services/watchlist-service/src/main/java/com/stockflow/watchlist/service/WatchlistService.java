package com.stockflow.watchlist.service;

import com.stockflow.watchlist.dto.*;
import com.stockflow.watchlist.model.*;
import com.stockflow.watchlist.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WatchlistService {
    private final WatchlistRepository watchlistRepo;
    private final WatchlistItemRepository itemRepo;

    public WatchlistService(WatchlistRepository watchlistRepo, WatchlistItemRepository itemRepo) {
        this.watchlistRepo = watchlistRepo;
        this.itemRepo = itemRepo;
    }

    @Transactional(readOnly = true)
    public List<WatchlistDTO> getWatchlists(UUID userId) {
        return watchlistRepo.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::toDTO).toList();
    }

    @Transactional
    public WatchlistDTO createWatchlist(UUID userId, CreateWatchlistRequest request) {
        Watchlist wl = new Watchlist();
        wl.setUserId(userId);
        wl.setName(request.getName());
        if (watchlistRepo.findByUserIdAndIsDefaultTrue(userId).isEmpty()) {
            wl.setDefault(true);
        }
        if (request.getSymbols() != null) {
            request.getSymbols().forEach(sym -> {
                WatchlistItem item = new WatchlistItem();
                item.setWatchlist(wl);
                item.setSymbol(sym.toUpperCase());
                wl.getItems().add(item);
            });
        }
        return toDTO(watchlistRepo.save(wl));
    }

    @Transactional
    public void addSymbol(UUID userId, UUID watchlistId, String symbol) {
        Watchlist wl = watchlistRepo.findById(watchlistId)
            .orElseThrow(() -> new IllegalArgumentException("Watchlist not found"));
        if (!wl.getUserId().equals(userId)) throw new IllegalArgumentException("Access denied");
        WatchlistItem item = new WatchlistItem();
        item.setWatchlist(wl);
        item.setSymbol(symbol.toUpperCase());
        wl.getItems().add(item);
        watchlistRepo.save(wl);
    }

    @Transactional
    public void removeSymbol(UUID userId, UUID watchlistId, String symbol) {
        Watchlist wl = watchlistRepo.findById(watchlistId)
            .orElseThrow(() -> new IllegalArgumentException("Watchlist not found"));
        if (!wl.getUserId().equals(userId)) throw new IllegalArgumentException("Access denied");
        itemRepo.deleteByWatchlistIdAndSymbol(watchlistId, symbol.toUpperCase());
    }

    @Transactional
    public void deleteWatchlist(UUID userId, UUID watchlistId) {
        Watchlist wl = watchlistRepo.findById(watchlistId)
            .orElseThrow(() -> new IllegalArgumentException("Watchlist not found"));
        if (!wl.getUserId().equals(userId)) throw new IllegalArgumentException("Access denied");
        watchlistRepo.delete(wl);
    }

    private WatchlistDTO toDTO(Watchlist wl) {
        WatchlistDTO dto = new WatchlistDTO();
        dto.setId(wl.getId());
        dto.setName(wl.getName());
        dto.setDefault(wl.isDefault());
        dto.setSymbols(wl.getItems().stream().map(WatchlistItem::getSymbol).toList());
        dto.setCreatedAt(wl.getCreatedAt());
        return dto;
    }
}
