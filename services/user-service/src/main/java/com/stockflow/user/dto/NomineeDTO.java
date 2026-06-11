package com.stockflow.user.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public class NomineeDTO {
    private UUID id;
    @NotBlank @Size(max = 150) private String name;
    @NotBlank @Size(max = 50) private String relation;
    @NotNull @DecimalMin("0.01") @DecimalMax("100.00") private BigDecimal percentage;
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }
    public BigDecimal getPercentage() { return percentage; }
    public void setPercentage(BigDecimal percentage) { this.percentage = percentage; }
}
