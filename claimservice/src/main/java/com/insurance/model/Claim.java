//package com.insurance.model;
//
//import java.time.LocalDate;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
////
////@Entity
////public class Claim {
////	@Id
////	@GeneratedValue(strategy = GenerationType.AUTO)
////	private Long claimId;
////	private Long policyId;
////	private Long customerId;
////	private String customerUsername;
////	private String claimType;
////	private String status="Pending";
////	private Double amount;
////	private String note;
////	private String documentType;
////	private String documentId;
////	private Double coverageAmount;
////    private LocalDate policyStartDate;
////    private LocalDate policyEndDate;
////    
////
////    public Claim() {
////		super();
////	}
////	public String getCustomerUsername() {
////		return customerUsername;
////	}
////	public void setCustomerUsername(String customerUsername) {
////		this.customerUsername = customerUsername;
////	}
////	public LocalDate getPolicyStartDate() {
////		return policyStartDate;
////	}
////	public void setPolicyStartDate(LocalDate policyStartDate) {
////		this.policyStartDate = policyStartDate;
////	}
////	public LocalDate getPolicyEndDate() {
////		return policyEndDate;
////	}
////	public void setPolicyEndDate(LocalDate policyEndDate) {
////		this.policyEndDate = policyEndDate;
////	}
////	public Long getClaimId() {
////		return claimId;
////	}
////	public void setClaimId(Long claimId) {
////		this.claimId = claimId;
////	}
////	public Long getPolicyId() {
////		return policyId;
////	}
////	public void setPolicyId(Long policyId) {
////		this.policyId = policyId;
////	}
////	public Long getCustomerId() {
////		return customerId;
////	}
////	public void setCustomerId(Long customerId) {
////		this.customerId = customerId;
////	}
////	public String getClaimType() {
////		return claimType;
////	}
////	public void setClaimType(String claimType) {
////		this.claimType = claimType;
////	}
////	public String getStatus() {
////		return status;
////	}
////	public void setStatus(String status) {
////		this.status = status;
////	}
////	public Double getAmount() {
////		return amount;
////	}
////	public void setAmount(Double amount) {
////		this.amount = amount;
////	}
////	public String getNote() {
////		return note;
////	}
////	public void setNote(String note) {
////		this.note = note;
////	}
////	public String getDocumentType() {
////		return documentType;
////	}
////	public void setDocumentType(String documentType) {
////		this.documentType = documentType;
////	}
////	public String getDocumentId() {
////		return documentId;
////	}
////	public void setDocumentId(String documentId) {
////		this.documentId = documentId;
////	}
////	public Double getCoverageAmount() {
////		return coverageAmount;
////	}
////	public void setCoverageAmount(Double coverageAmount) {
////		this.coverageAmount = coverageAmount;
////	}
////	
////	
////	
////	public Claim(Long claimId, Long policyId, Long customerId, String customerUsername, String claimType, String status,
////			Double amount, String note, String documentType, String documentId, Double coverageAmount,
////			LocalDate policyStartDate, LocalDate policyEndDate) {
////		super();
////		this.claimId = claimId;
////		this.policyId = policyId;
////		this.customerId = customerId;
////		this.customerUsername = customerUsername;
////		this.claimType = claimType;
////		this.status = status;
////		this.amount = amount;
////		this.note = note;
////		this.documentType = documentType;
////		this.documentId = documentId;
////		this.coverageAmount = coverageAmount;
////		this.policyStartDate = policyStartDate;
////		this.policyEndDate = policyEndDate;
////	}
////	@Override
////	public String toString() {
////		return "Claim [claimId=" + claimId + ", policyId=" + policyId + ", customerId=" + customerId + ", claimType="
////				+ claimType + ", status=" + status + ", amount=" + amount + ", note=" + note + ", documentType="
////				+ documentType + ", documentId=" + documentId + ", coverageAmount=" + coverageAmount
////				+ ", policyStartDate=" + policyStartDate + ", policyEndDate=" + policyEndDate + "]";
////	}
////	
////	
////	
////	
////	
////	
////	
////	
////
////}
//
//
//@Entity
//public class Claim {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long claimId;
//
//    private Long policyId;
//
//    private Long customerId; // optional
//    private String customerUsername; // 🔥 IMPORTANT
//
//    private String claimType;
//    private String status; // PENDING / APPROVED / REJECTED
//    private Double amount;
//    private String note;
//
//    private String documentType;
//    private String documentId;
//
//    private Double coverageAmount;
//    private LocalDate policyStartDate;
//    private LocalDate policyEndDate;
//	public Claim() {
//		super();
//	}
//	public Claim(Long claimId, Long policyId, Long customerId, String customerUsername, String claimType, String status,
//			Double amount, String note, String documentType, String documentId, Double coverageAmount,
//			LocalDate policyStartDate, LocalDate policyEndDate) {
//		super();
//		this.claimId = claimId;
//		this.policyId = policyId;
//		this.customerId = customerId;
//		this.customerUsername = customerUsername;
//		this.claimType = claimType;
//		this.status = status;
//		this.amount = amount;
//		this.note = note;
//		this.documentType = documentType;
//		this.documentId = documentId;
//		this.coverageAmount = coverageAmount;
//		this.policyStartDate = policyStartDate;
//		this.policyEndDate = policyEndDate;
//	}
//	public Long getClaimId() {
//		return claimId;
//	}
//	public void setClaimId(Long claimId) {
//		this.claimId = claimId;
//	}
//	public Long getPolicyId() {
//		return policyId;
//	}
//	public void setPolicyId(Long policyId) {
//		this.policyId = policyId;
//	}
//	public Long getCustomerId() {
//		return customerId;
//	}
//	public void setCustomerId(Long customerId) {
//		this.customerId = customerId;
//	}
//	public String getCustomerUsername() {
//		return customerUsername;
//	}
//	public void setCustomerUsername(String customerUsername) {
//		this.customerUsername = customerUsername;
//	}
//	public String getClaimType() {
//		return claimType;
//	}
//	public void setClaimType(String claimType) {
//		this.claimType = claimType;
//	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	public Double getAmount() {
//		return amount;
//	}
//	public void setAmount(Double amount) {
//		this.amount = amount;
//	}
//	public String getNote() {
//		return note;
//	}
//	public void setNote(String note) {
//		this.note = note;
//	}
//	public String getDocumentType() {
//		return documentType;
//	}
//	public void setDocumentType(String documentType) {
//		this.documentType = documentType;
//	}
//	public String getDocumentId() {
//		return documentId;
//	}
//	public void setDocumentId(String documentId) {
//		this.documentId = documentId;
//	}
//	public Double getCoverageAmount() {
//		return coverageAmount;
//	}
//	public void setCoverageAmount(Double coverageAmount) {
//		this.coverageAmount = coverageAmount;
//	}
//	public LocalDate getPolicyStartDate() {
//		return policyStartDate;
//	}
//	public void setPolicyStartDate(LocalDate policyStartDate) {
//		this.policyStartDate = policyStartDate;
//	}
//	public LocalDate getPolicyEndDate() {
//		return policyEndDate;
//	}
//	public void setPolicyEndDate(LocalDate policyEndDate) {
//		this.policyEndDate = policyEndDate;
//	}
//
//    // getters & setters
//    
//}

package com.insurance.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;
    private Long customerId;
    private Long policyId;
    private String customerUsername; // 🔥 PRIMARY IDENTIFIER
    private String claimType;
    private String status; // PENDING / APPROVED / REJECTED
    private Double amount;
    private String note;
    private String documentType;
    private String documentId;
    private Double coverageAmount;
    private LocalDate policyStartDate;
    private LocalDate policyEndDate;

    public Claim() {}

    // Getters and Setters
    public Long getClaimId() { return claimId; }
    public void setClaimId(Long claimId) { this.claimId = claimId; }

    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }

    public String getCustomerUsername() { return customerUsername; }
    public void setCustomerUsername(String customerUsername) { this.customerUsername = customerUsername; }

    public String getClaimType() { return claimType; }
    public void setClaimType(String claimType) { this.claimType = claimType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }

    public Double getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(Double coverageAmount) { this.coverageAmount = coverageAmount; }

    public LocalDate getPolicyStartDate() { return policyStartDate; }
    public void setPolicyStartDate(LocalDate policyStartDate) { this.policyStartDate = policyStartDate; }

    public LocalDate getPolicyEndDate() { return policyEndDate; }
    public void setPolicyEndDate(LocalDate policyEndDate) { this.policyEndDate = policyEndDate; }

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
    
}

