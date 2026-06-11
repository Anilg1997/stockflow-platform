package com.stockflow.watchlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class CreateWatchlistRequest {
    @NotBlank @Size(max = 100) private String name;
    private List<String> symbols;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getSymbols() { return symbols; }
    public void setSymbols(List<String> symbols) { this.symbols = symbols; }
}
