package com.stockflow.mf.service;

import com.stockflow.mf.model.MutualFund;
import com.stockflow.mf.repository.MutualFundRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MfService {
    private final MutualFundRepository repo;
    public MfService(MutualFundRepository repo) { this.repo = repo; }
    public List<MutualFund> getAllFunds() { return repo.findAll(); }
    public List<MutualFund> getByCategory(String category) { return repo.findByCategory(category); }
    public List<MutualFund> search(String query) { return repo.findBySchemeNameContainingIgnoreCase(query); }
}
