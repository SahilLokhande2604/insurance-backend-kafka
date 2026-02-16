package com.policy.service;

//import com.policy.kafka.PolicyEventProducer;

import com.policy.model.Policy;
import com.policy.repository.PolicyRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PolicyService {


    private final PolicyRepository policyRepository;
    
public PolicyService(PolicyRepository policyRepository) {
		super();
		this.policyRepository = policyRepository;
	}

//    private final PolicyEventProducer policyEventProducer;
    

//    public PolicyService(PolicyRepository policyRepository, PolicyEventProducer policyEventProducer) {
//        this.policyRepository = policyRepository;
//        this.policyEventProducer = policyEventProducer;
//    }

    public Policy createPolicy(Policy policy) {
        policy.setCreatedAt(LocalDateTime.now());
        policy.setUpdatedAt(LocalDateTime.now());
        Policy saved = policyRepository.save(policy);
//        policyEventProducer.sendPolicyPurchaseEvent("New policy created: " + saved.getPolicyName() + " (ID: " + saved.getId() + ")");
        return saved;
    }

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
    }
    
    public Policy updatePolicy(Long id, Policy updatedPolicy) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        policy.setPolicyName(updatedPolicy.getPolicyName());
        policy.setPolicyType(updatedPolicy.getPolicyType());
        policy.setCoverageAmount(updatedPolicy.getCoverageAmount());
        policy.setPremiumAmount(updatedPolicy.getPremiumAmount());
        policy.setDescription(updatedPolicy.getDescription());
        policy.setUpdatedAt(LocalDateTime.now());

        Policy saved = policyRepository.save(policy);
//        policyEventProducer.sendPolicyUpdateEvent("Policy updated: " + saved.getPolicyName() + " (ID: " + saved.getId() + ")");
        return saved;
    }

    public void deletePolicy(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
        policyRepository.delete(policy);
    }

}
