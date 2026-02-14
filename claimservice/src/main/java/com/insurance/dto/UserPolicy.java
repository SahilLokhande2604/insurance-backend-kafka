package com.insurance.dto;



import java.time.LocalDateTime;

public class UserPolicy {

    private Long id;
    private String username;
    private Policy policy;
    private LocalDateTime purchaseDate;
    private LocalDateTime expiryDate;
    private String status;

    public UserPolicy() {}

    public UserPolicy(Long id, String username, Policy policy,
                      LocalDateTime purchaseDate, LocalDateTime expiryDate,
                      String status) {
        this.id = id;
        this.username = username;
        this.policy = policy;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Policy getPolicy() { return policy; }
    public void setPolicy(Policy policy) { this.policy = policy; }

    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
