package com.policy.dto;

public class UserPolicyRequest {

    private Long userId;
    private Long policyId;
    private Long paymentId;

    public UserPolicyRequest() {}

    public UserPolicyRequest(Long userId, Long policyId, Long paymentId) {
        this.userId = userId;
        this.policyId = policyId;
        this.paymentId = paymentId;
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

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

}
