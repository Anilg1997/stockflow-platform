package com.stockflow.mfholdings.service;

import com.stockflow.mfholdings.model.MfHolding;
import com.stockflow.mfholdings.repository.MfHoldingRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class MfHoldingsService {
    private final MfHoldingRepository repo;
    public MfHoldingsService(MfHoldingRepository repo) { this.repo = repo; }
    public List<MfHolding> getHoldings(UUID userId) { return repo.findByUserId(userId); }
}
