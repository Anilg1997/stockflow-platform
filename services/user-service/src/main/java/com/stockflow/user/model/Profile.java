package com.stockflow.user.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema = "user_schema", name = "profiles")
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", unique = true, nullable = false)
    private UUID userId;
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;
    @Column(name = "pan_number", length = 10)
    private String panNumber;
    @Column(name = "aadhaar_last4", length = 4)
    private String aadhaarLast4;
    private LocalDate dob;
    @Column(name = "kyc_status", length = 20)
    private String kycStatus = "PENDING";
    @Column(name = "kyc_reviewed_at")
    private Instant kycReviewedAt;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @PrePersist
    protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate
    protected void onUpdate() { updatedAt = Instant.now(); }
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
    public Instant getKycReviewedAt() { return kycReviewedAt; }
    public void setKycReviewedAt(Instant kycReviewedAt) { this.kycReviewedAt = kycReviewedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
