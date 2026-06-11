package com.stockflow.user.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "user_schema", name = "bank_accounts")
public class BankAccount {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "bank_name", nullable = false, length = 100)
    private String bankName;
    @Column(name = "account_number", nullable = false, length = 20)
    private String accountNumber;
    @Column(nullable = false, length = 11)
    private String ifsc;
    @Column(name = "is_primary")
    private boolean isPrimary;
    @Column(name = "is_verified")
    private boolean isVerified;
    @Column(name = "added_at")
    private Instant addedAt;
    @PrePersist
    protected void onCreate() { addedAt = Instant.now(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getIfsc() { return ifsc; }
    public void setIfsc(String ifsc) { this.ifsc = ifsc; }
    public boolean isPrimary() { return isPrimary; }
    public void setPrimary(boolean primary) { isPrimary = primary; }
    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }
    public Instant getAddedAt() { return addedAt; }
}
