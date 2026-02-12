package com.insurance.payment.repository;

import com.insurance.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId); // Add this for user payments endpoint
    
    Optional<Payment> findTopByUserIdAndPolicyNumberOrderByCreatedAtDesc(Long userId, String policyNumber);

}