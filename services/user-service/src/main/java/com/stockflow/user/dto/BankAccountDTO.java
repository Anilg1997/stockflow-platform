package com.stockflow.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public class BankAccountDTO {
    private UUID id;
    @NotBlank private String bankName;
    @NotBlank private String accountNumber;
    @NotBlank @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$") private String ifsc;
    private boolean isPrimary;
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getIfsc() { return ifsc; }
    public void setIfsc(String ifsc) { this.ifsc = ifsc; }
    public boolean isPrimary() { return isPrimary; }
    public void setPrimary(boolean primary) { isPrimary = primary; }
}
