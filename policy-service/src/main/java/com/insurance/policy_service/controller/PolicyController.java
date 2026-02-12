package com.insurance.policy_service.controller;

import com.insurance.policy_service.model.policy.Policy;
import com.insurance.policy_service.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies() {
        List<Policy> policies = policyService.getAllPolicies();
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Policy>> getPoliciesByUser(@PathVariable Long userId) {
        List<Policy> policies = policyService.getPoliciesByUserId(userId);
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {
        Policy policy = policyService.getPolicyById(id);
        if (policy != null) {
            return ResponseEntity.ok(policy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {
        Policy createdPolicy = policyService.createPolicy(policy);
        return ResponseEntity.ok(createdPolicy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long id, @RequestBody Policy policy) {
        Policy updatedPolicy = policyService.updatePolicy(id, policy);
        if (updatedPolicy != null) {
            return ResponseEntity.ok(updatedPolicy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        boolean deleted = policyService.deletePolicy(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
