package com.customersupport.repo;

//src/main/java/com/insurance/support/repo/TicketRepository.java

import com.customersupport.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
 List<Ticket> findByRaisedBy(String raisedBy);
 List<Ticket> findByPolicyId(Long policyId);
}