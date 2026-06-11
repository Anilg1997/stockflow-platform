package com.stockflow.user.service;

import com.stockflow.user.dto.*;
import com.stockflow.user.model.*;
import com.stockflow.user.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final ProfileRepository profileRepo;
    private final BankAccountRepository bankRepo;
    private final NomineeRepository nomineeRepo;

    public UserService(ProfileRepository profileRepo, BankAccountRepository bankRepo, NomineeRepository nomineeRepo) {
        this.profileRepo = profileRepo;
        this.bankRepo = bankRepo;
        this.nomineeRepo = nomineeRepo;
    }

    // ── Profile ──────────────────────────────────────────────
    public ProfileDTO getProfile(UUID userId) {
        return profileRepo.findByUserId(userId).map(this::toDTO)
            .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
    }

    @Transactional
    public ProfileDTO updateKYC(UUID userId, KYCRequest request) {
        Profile profile = profileRepo.findByUserId(userId)
            .orElseGet(() -> {
                Profile p = new Profile();
                p.setUserId(userId);
                return p;
            });
        profile.setFullName(request.getFullName());
        profile.setPanNumber(request.getPanNumber());
        profile.setAadhaarLast4(request.getAadhaarLast4());
        profile.setDob(request.getDob());
        profile.setKycStatus("VERIFIED");
        profile.setKycReviewedAt(Instant.now());
        return toDTO(profileRepo.save(profile));
    }

    // ── Bank Accounts ────────────────────────────────────────
    public List<BankAccountDTO> getBankAccounts(UUID userId) {
        return bankRepo.findByUserIdOrderByAddedAtDesc(userId).stream().map(this::toDTO).toList();
    }

    @Transactional
    public BankAccountDTO addBankAccount(UUID userId, BankAccountDTO dto) {
        BankAccount ba = new BankAccount();
        ba.setUserId(userId);
        ba.setBankName(dto.getBankName());
        ba.setAccountNumber(dto.getAccountNumber());
        ba.setIfsc(dto.getIfsc());
        if (dto.isPrimary() || bankRepo.findByUserIdAndIsPrimaryTrue(userId).isEmpty()) {
            if (dto.isPrimary()) {
                bankRepo.findByUserIdAndIsPrimaryTrue(userId).ifPresent(b -> { b.setPrimary(false); bankRepo.save(b); });
            }
            ba.setPrimary(true);
        }
        return toDTO(bankRepo.save(ba));
    }

    @Transactional
    public void deleteBankAccount(UUID userId, UUID accountId) {
        BankAccount ba = bankRepo.findById(accountId)
            .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
        if (!ba.getUserId().equals(userId)) throw new IllegalArgumentException("Access denied");
        bankRepo.delete(ba);
    }

    // ── Nominees ─────────────────────────────────────────────
    public List<NomineeDTO> getNominees(UUID userId) {
        return nomineeRepo.findByUserId(userId).stream().map(this::toDTO).toList();
    }

    @Transactional
    public NomineeDTO addNominee(UUID userId, NomineeDTO dto) {
        Nominee n = new Nominee();
        n.setUserId(userId);
        n.setName(dto.getName());
        n.setRelation(dto.getRelation());
        n.setPercentage(dto.getPercentage());
        return toDTO(nomineeRepo.save(n));
    }

    @Transactional
    public void deleteNominee(UUID userId, UUID nomineeId) {
        Nominee n = nomineeRepo.findById(nomineeId)
            .orElseThrow(() -> new IllegalArgumentException("Nominee not found"));
        if (!n.getUserId().equals(userId)) throw new IllegalArgumentException("Access denied");
        nomineeRepo.delete(n);
    }

    // ── Mappers ──────────────────────────────────────────────
    private ProfileDTO toDTO(Profile p) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(p.getId()); dto.setUserId(p.getUserId()); dto.setFullName(p.getFullName());
        dto.setPanNumber(p.getPanNumber()); dto.setAadhaarLast4(p.getAadhaarLast4());
        dto.setDob(p.getDob()); dto.setKycStatus(p.getKycStatus()); dto.setCreatedAt(p.getCreatedAt());
        return dto;
    }
    private BankAccountDTO toDTO(BankAccount ba) {
        BankAccountDTO dto = new BankAccountDTO();
        dto.setId(ba.getId()); dto.setBankName(ba.getBankName()); dto.setAccountNumber(ba.getAccountNumber());
        dto.setIfsc(ba.getIfsc()); dto.setPrimary(ba.isPrimary());
        return dto;
    }
    private NomineeDTO toDTO(Nominee n) {
        NomineeDTO dto = new NomineeDTO();
        dto.setId(n.getId()); dto.setName(n.getName()); dto.setRelation(n.getRelation()); dto.setPercentage(n.getPercentage());
        return dto;
    }
}
