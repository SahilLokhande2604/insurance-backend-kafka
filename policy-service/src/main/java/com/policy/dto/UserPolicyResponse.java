package com.policy.dto;

import java.time.LocalDateTime;

public class UserPolicyResponse {

    private Long id;
    private Long userId;
    private Long policyId;
    private String status;
    private LocalDateTime purchaseDate;
    private LocalDateTime expiryDate;

    public UserPolicyResponse() {}

    public UserPolicyResponse(Long id, Long userId, Long policyId,
                              String status,
                              LocalDateTime purchaseDate,
                              LocalDateTime expiryDate) {
        this.id = id;
        this.userId = userId;
        this.policyId = policyId;
        this.status = status;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

}
