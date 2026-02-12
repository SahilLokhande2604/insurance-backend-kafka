package com.insurance.payment.services;

import com.insurance.payment.domain.Payment;
import com.insurance.payment.domain.PaymentStatus;
import com.insurance.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repo;

    @Transactional
    public Payment createPayment(Long userId, String policyNumber, Double amount) { // <-- String if frontend POSTs policyNumber!
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setPolicyNumber(policyNumber); // <-- Policy number, not ID
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.INITIATED);
        payment.setTransactionReference("REF" + System.currentTimeMillis());
        return repo.save(payment);
    }

    public Payment updateStatus(Long paymentId, PaymentStatus status) {
        Payment payment = repo.findById(paymentId).orElseThrow();
        payment.setStatus(status);
        return repo.save(payment);
    }
    

public Optional<Payment> getLatestUserPaymentForPolicy(Long userId, String policyNumber) {
    return repo.findTopByUserIdAndPolicyNumberOrderByCreatedAtDesc(userId, policyNumber);
}

    public Payment getPayment(Long paymentId) {
        return repo.findById(paymentId).orElse(null);
    }

    // List of all payments for a user (add a method in controller for this)
    public List<Payment> getByUserId(Long userId) {
        return repo.findByUserId(userId);
    }
  

    // List of all payments (for admin)
    public List<Payment> getAll() {
        return repo.findAll();
    }
}