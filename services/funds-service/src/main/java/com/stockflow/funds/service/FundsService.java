package com.stockflow.funds.service;

import com.stockflow.common.constants.ServiceConstants;
import com.stockflow.funds.dto.*;
import com.stockflow.funds.model.*;
import com.stockflow.funds.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class FundsService {
    private final WalletRepository walletRepo;
    private final FundTransactionRepository txRepo;

    public FundsService(WalletRepository walletRepo, FundTransactionRepository txRepo) {
        this.walletRepo = walletRepo;
        this.txRepo = txRepo;
    }

    public WalletDTO getWallet(UUID userId) {
        Wallet wallet = walletRepo.findByUserId(userId)
            .orElseGet(() -> createWallet(userId));
        return toDTO(wallet);
    }

    @Transactional
    public WalletDTO deposit(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        Wallet wallet = walletRepo.findByUserId(userId).orElseGet(() -> createWallet(userId));
        wallet.setAvailableBalance(wallet.getAvailableBalance().add(amount));

        FundTransaction tx = new FundTransaction();
        tx.setUserId(userId);
        tx.setType("DEPOSIT");
        tx.setAmount(amount);
        tx.setReference("DEP-" + UUID.randomUUID().toString().substring(0, 8));
        tx.setDescription("Funds added to wallet");
        tx.setStatus("SUCCESS");
        txRepo.save(tx);

        return toDTO(walletRepo.save(wallet));
    }

    @Transactional
    public WalletDTO withdraw(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        Wallet wallet = walletRepo.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        BigDecimal available = wallet.getAvailableBalance().subtract(wallet.getOnHold());
        if (amount.compareTo(available) > 0)
            throw new IllegalArgumentException("Insufficient balance");

        wallet.setAvailableBalance(wallet.getAvailableBalance().subtract(amount));

        FundTransaction tx = new FundTransaction();
        tx.setUserId(userId);
        tx.setType("WITHDRAW");
        tx.setAmount(amount);
        tx.setReference("WTH-" + UUID.randomUUID().toString().substring(0, 8));
        tx.setDescription("Funds withdrawn from wallet");
        tx.setStatus("SUCCESS");
        txRepo.save(tx);

        return toDTO(walletRepo.save(wallet));
    }

    public List<FundTransaction> getTransactions(UUID userId) {
        return txRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Page<FundTransaction> getTransactionsPaged(UUID userId, Pageable pageable) {
        return txRepo.findByUserId(userId, pageable);
    }

    private Wallet createWallet(UUID userId) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setAvailableBalance(BigDecimal.ZERO);
        wallet.setOnHold(BigDecimal.ZERO);
        return walletRepo.save(wallet);
    }

    private WalletDTO toDTO(Wallet w) {
        WalletDTO dto = new WalletDTO();
        dto.setId(w.getId());
        dto.setUserId(w.getUserId());
        dto.setAvailableBalance(w.getAvailableBalance());
        dto.setOnHold(w.getOnHold());
        return dto;
    }
}
