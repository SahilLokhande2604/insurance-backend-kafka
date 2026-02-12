package com.insurance.policy_service.model.policy;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "policies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("policyNumber")
    @Column(nullable = false, unique = true)
    private String policyNumber;

    @JsonProperty("policyType")
    @Column(nullable = false)
    private String policyType;

    @JsonProperty("coverageAmount")
    @Column(nullable = false)
    private Double coverageAmount;

    @JsonProperty("premiumAmount")
    @Column(nullable = false)
    private Double premiumAmount;

    @JsonProperty("status")
    @Column(nullable = false)
    private String status;

    @JsonProperty("userId")
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
