package com.stockflow.search.service;

import com.stockflow.search.model.SearchResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchService {
    private final JdbcTemplate jdbc;
    public SearchService(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public List<SearchResult> search(String query) {
        String searchTerm = "%" + query.toLowerCase() + "%";
        String sql = """
            SELECT s.symbol, s.name, 'STOCK' as type, s.sector,
                   sp.current_price, sp.change_percent
            FROM market_schema.stocks s
            LEFT JOIN market_schema.stock_prices sp ON sp.symbol = s.symbol
            WHERE LOWER(s.symbol) LIKE ? OR LOWER(s.name) LIKE ? OR LOWER(s.sector) LIKE ?
            LIMIT 20
            """;
        return jdbc.query(sql, (rs, row) -> {
            SearchResult r = new SearchResult();
            r.setSymbol(rs.getString("symbol"));
            r.setName(rs.getString("name"));
            r.setType(rs.getString("type"));
            r.setSector(rs.getString("sector"));
            r.setCurrentPrice(rs.getBigDecimal("current_price"));
            r.setChangePercent(rs.getBigDecimal("change_percent"));
            return r;
        }, searchTerm, searchTerm, searchTerm);
    }

    public List<SearchResult> searchByType(String query, String type) {
        return search(query).stream().filter(r -> r.getType().equals(type)).toList();
    }
}
