package com.insurance.payment.controller;

import com.insurance.payment.domain.Payment;
import com.insurance.payment.domain.PaymentStatus;
import com.insurance.payment.services.PaymentService;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public Payment initiatePayment(@RequestBody PaymentRequest req) {
        // Use req.policyNumber() if your PaymentRequest is updated accordingly, or req.policyId()
        return paymentService.createPayment(req.userId(), req.policyNumber(), req.amount()); // or policyId()
    }

    @PatchMapping("/{paymentId}/status")
    public Payment updatePaymentStatus(@PathVariable Long paymentId, @RequestParam PaymentStatus status) {
        return paymentService.updateStatus(paymentId, status);
    }
    
    @GetMapping("/user/{userId}")
    public List<Payment> getUserPayments(@PathVariable Long userId) {
        return paymentService.getByUserId(userId);
    }
    
    @GetMapping("/user/{userId}/policy/{policyNumber}")
    public Payment getUserPaymentForPolicy(@PathVariable Long userId, @PathVariable String policyNumber) {
        return paymentService.getLatestUserPaymentForPolicy(userId, policyNumber).orElse(null);
    }

    @GetMapping("/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }
}

// Adjust this record to match what your frontend is sending!
record PaymentRequest(Long userId, String policyNumber, Double amount) {}