package com.stockflow.marketdata.service;

import com.stockflow.marketdata.dto.*;
import com.stockflow.marketdata.model.*;
import com.stockflow.marketdata.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MarketDataService {
    private final StockRepository stockRepo;
    private final StockPriceRepository priceRepo;
    private final MarketIndexRepository indexRepo;

    public MarketDataService(StockRepository stockRepo, StockPriceRepository priceRepo, MarketIndexRepository indexRepo) {
        this.stockRepo = stockRepo;
        this.priceRepo = priceRepo;
        this.indexRepo = indexRepo;
    }

    public List<StockDTO> getAllStocks() {
        return stockRepo.findByIsActiveTrue().stream().map(s -> {
            StockDTO dto = toStockDTO(s);
            priceRepo.findBySymbol(s.getSymbol()).ifPresent(p -> enrichWithPrice(dto, p));
            return dto;
        }).toList();
    }

    public StockDTO getStockBySymbol(String symbol) {
        Stock stock = stockRepo.findBySymbol(symbol.toUpperCase())
            .orElseThrow(() -> new IllegalArgumentException("Stock not found: " + symbol));
        StockDTO dto = toStockDTO(stock);
        priceRepo.findBySymbol(stock.getSymbol()).ifPresent(p -> enrichWithPrice(dto, p));
        return dto;
    }

    public List<StockDTO> searchStocks(String query) {
        return stockRepo.findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCase(query, query)
            .stream().map(s -> {
                StockDTO dto = toStockDTO(s);
                priceRepo.findBySymbol(s.getSymbol()).ifPresent(p -> enrichWithPrice(dto, p));
                return dto;
            }).toList();
    }

    public List<StockDTO> getStocksBySector(String sector) {
        return stockRepo.findBySectorIgnoreCase(sector).stream().map(s -> {
            StockDTO dto = toStockDTO(s);
            priceRepo.findBySymbol(s.getSymbol()).ifPresent(p -> enrichWithPrice(dto, p));
            return dto;
        }).toList();
    }

    public List<IndexDTO> getAllIndices() {
        return indexRepo.findAll().stream().map(this::toIndexDTO).toList();
    }

    public List<String> getAllSectors() {
        return stockRepo.findByIsActiveTrue().stream()
            .map(Stock::getSector).distinct().sorted().toList();
    }

    public StockDTO getMarketSummary() {
        StockDTO summary = new StockDTO();
        summary.setName("Market Summary");
        long totalStocks = stockRepo.count();
        summary.setVolume(totalStocks);
        return summary;
    }

    private StockDTO toStockDTO(Stock s) {
        StockDTO dto = new StockDTO();
        dto.setId(s.getId()); dto.setSymbol(s.getSymbol()); dto.setName(s.getName());
        dto.setExchange(s.getExchange()); dto.setSector(s.getSector());
        return dto;
    }

    private void enrichWithPrice(StockDTO dto, StockPrice p) {
        dto.setCurrentPrice(p.getCurrentPrice()); dto.setOpen(p.getOpen());
        dto.setHigh(p.getHigh()); dto.setLow(p.getLow()); dto.setClose(p.getClose());
        dto.setChange(p.getChange()); dto.setChangePercent(p.getChangePercent());
        dto.setWeekHigh52(p.getWeekHigh52()); dto.setWeekLow52(p.getWeekLow52());
        dto.setVolume(p.getVolume());
    }

    private IndexDTO toIndexDTO(MarketIndex idx) {
        IndexDTO dto = new IndexDTO();
        dto.setId(idx.getId()); dto.setName(idx.getName()); dto.setValue(idx.getValue());
        dto.setChange(idx.getChange()); dto.setChangePercent(idx.getChangePercent());
        return dto;
    }
}
