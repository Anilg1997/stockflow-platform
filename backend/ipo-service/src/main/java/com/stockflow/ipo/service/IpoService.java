package com.stockflow.ipo.service;

import com.stockflow.ipo.model.*;
import com.stockflow.ipo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class IpoService {
    private final IpoRepository ipoRepo;
    private final IpoApplicationRepository appRepo;
    public IpoService(IpoRepository ipoRepo, IpoApplicationRepository appRepo) {
        this.ipoRepo = ipoRepo; this.appRepo = appRepo;
    }
    public List<Ipo> getAllIpos() { return ipoRepo.findByOrderByOpenDateDesc(); }
    public List<Ipo> getIposByStatus(String status) { return ipoRepo.findByStatusOrderByOpenDateDesc(status); }
    @Transactional
    public IpoApplication apply(UUID userId, UUID ipoId, int lots, BigDecimal bidPrice, String upiId) {
        Ipo ipo = ipoRepo.findById(ipoId).orElseThrow(() -> new IllegalArgumentException("IPO not found"));
        IpoApplication app = new IpoApplication();
        app.setUserId(userId); app.setIpo(ipo); app.setLots(lots);
        app.setBidPrice(bidPrice); app.setUpiId(upiId);
        return appRepo.save(app);
    }
    public List<IpoApplication> getUserApplications(UUID userId) { return appRepo.findByUserId(userId); }
}
