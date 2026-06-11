package com.stockflow.sip.service;

import com.stockflow.sip.model.Sip;
import com.stockflow.sip.repository.SipRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SipService {
    private final SipRepository repo;
    public SipService(SipRepository repo) { this.repo = repo; }

    @Transactional
    public Sip createSip(UUID userId, String schemeCode, java.math.BigDecimal amount, int sipDate, LocalDate startDate) {
        Sip sip = new Sip();
        sip.setUserId(userId); sip.setSchemeCode(schemeCode); sip.setMonthlyAmount(amount);
        sip.setSipDate(sipDate); sip.setStartDate(startDate);
        sip.setNextExecution(startDate);
        return repo.save(sip);
    }

    public List<Sip> getUserSips(UUID userId) { return repo.findByUserIdOrderByCreatedAtDesc(userId); }

    @Transactional
    public void pauseSip(UUID sipId) {
        repo.findById(sipId).ifPresent(s -> { s.setStatus("PAUSED"); repo.save(s); });
    }

    @Transactional
    public void activateSip(UUID sipId) {
        repo.findById(sipId).ifPresent(s -> { s.setStatus("ACTIVE"); repo.save(s); });
    }

    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void executeSips() {
        LocalDate today = LocalDate.now();
        List<Sip> dueSips = repo.findByStatusAndNextExecutionLessThanEqual("ACTIVE", today);
        for (Sip sip : dueSips) {
            sip.setNextExecution(today.plusMonths(1));
            repo.save(sip);
        }
    }
}
