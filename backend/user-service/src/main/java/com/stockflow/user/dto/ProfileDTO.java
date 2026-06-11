package com.stockflow.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private UUID id;
    private UUID userId;
    private String fullName;
    private String panNumber;
    private String aadhaarLast4;
    private LocalDate dob;
    private String kycStatus;
    private Instant createdAt;
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }
    public String getAadhaarLast4() { return aadhaarLast4; }
    public void setAadhaarLast4(String aadhaarLast4) { this.aadhaarLast4 = aadhaarLast4; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public String getKycStatus() { return kycStatus; }
    public void setKycStatus(String kycStatus) { this.kycStatus = kycStatus; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
