package com.insurance.policy_service.service;

import com.insurance.policy_service.model.policy.Policy;
import com.insurance.policy_service.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }
    
    public List<Policy> getPoliciesByUserId(Long userId) {
        return policyRepository.findByUserId(userId);
    }

    public Policy getPolicyById(Long id) {
        Optional<Policy> policy = policyRepository.findById(id);
        return policy.orElse(null);
    }

    public Policy createPolicy(Policy policy) {
        // Log the policy object received
        System.out.println("Creating policy: " + policy);
        // Auto-generate policy number if not provided
        if (policy.getPolicyNumber() == null || policy.getPolicyNumber().trim().isEmpty()) {
            policy.setPolicyNumber("POL" + System.currentTimeMillis());
        }
        return policyRepository.save(policy);
    }

    public Policy updatePolicy(Long id, Policy policyDetails) {
        Optional<Policy> optionalPolicy = policyRepository.findById(id);
        if (optionalPolicy.isPresent()) {
            Policy policy = optionalPolicy.get();
            policy.setPolicyNumber(policyDetails.getPolicyNumber());
            policy.setPolicyType(policyDetails.getPolicyType());
            policy.setCoverageAmount(policyDetails.getCoverageAmount());
            policy.setPremiumAmount(policyDetails.getPremiumAmount());
            policy.setStatus(policyDetails.getStatus());
            policy.setUserId(policyDetails.getUserId());
            return policyRepository.save(policy);
        }
        return null;
    }

    public boolean deletePolicy(Long id) {
        if (policyRepository.existsById(id)) {
            policyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
