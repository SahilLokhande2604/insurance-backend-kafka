package com.insurance.dto;

import java.time.LocalDate;

public class Policy {

    private Long policyId;
    private String policyName;
    private String policyCategory;
    private Double coverageAmount;
    private LocalDate policyStartDate;
    private LocalDate policyEndDate;

    public Policy() {}

    public Policy(Long policyId, String policyName, String policyCategory,
                  Double coverageAmount, LocalDate policyStartDate, LocalDate policyEndDate) {
        this.policyId = policyId;
        this.policyName = policyName;
        this.policyCategory = policyCategory;
        this.coverageAmount = coverageAmount;
        this.policyStartDate = policyStartDate;
        this.policyEndDate = policyEndDate;
    }

    // Getters & Setters
    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }

    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public String getPolicyCategory() { return policyCategory; }
    public void setPolicyCategory(String policyCategory) { this.policyCategory = policyCategory; }

    public Double getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(Double coverageAmount) { this.coverageAmount = coverageAmount; }

    public LocalDate getPolicyStartDate() { return policyStartDate; }
    public void setPolicyStartDate(LocalDate policyStartDate) { this.policyStartDate = policyStartDate; }

    public LocalDate getPolicyEndDate() { return policyEndDate; }
    public void setPolicyEndDate(LocalDate policyEndDate) { this.policyEndDate = policyEndDate; }
}
