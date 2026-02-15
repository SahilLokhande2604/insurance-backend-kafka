package com.customersupport.service;

//src/main/java/com/insurance/support/service/SupportService.java

import com.customersupport.client.PolicyClient;
import com.customersupport.dto.PolicyChangeRequest;
import com.customersupport.dto.PolicyDto;
import com.customersupport.exception.NotFoundException;
import com.customersupport.model.Ticket;
import com.customersupport.model.TicketType;
import com.customersupport.repo.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.customersupport.events.TicketCreatedEvent;
import com.customersupport.events.PolicyChangeRequestedEvent;
import com.customersupport.messaging.TicketEventProducer;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportService {

 private final PolicyClient policyClient;
 private final TicketRepository ticketRepo;
 private final TicketEventProducer eventProducer;

 // Browse all policies (catalog)
 public List<PolicyDto> listAllPolicies() {
     return policyClient.getAllPolicies();
 }

 // View user's policies
 public List<PolicyDto> listPoliciesByUser(Long userId) {
     return policyClient.getPoliciesByUserId(userId);
 }

 public PolicyDto getPolicy(Long policyId) {
     return policyClient.getPolicyById(policyId);
 }
 
 public Ticket raiseIssue(String raisedBy, Long policyId, String subject, String description) {
     Ticket t = new Ticket();
     t.setRaisedBy(raisedBy == null ? "anonymous" : raisedBy);
     t.setPolicyId(policyId);
     t.setType(TicketType.ISSUE);
     t.setSubject(subject);
     t.setDescription(description);
     t.setCreatedAt(OffsetDateTime.now());
     t.setUpdatedAt(OffsetDateTime.now());

     Ticket saved = ticketRepo.save(t);

     // Publish event
     eventProducer.publishTicketCreated(new TicketCreatedEvent(
             saved.getId(),
             saved.getType().name(),
             saved.getSubject(),
             saved.getDescription(),
             saved.getPolicyId(),
             saved.getRaisedBy(),
             saved.getCreatedAt()
     ));

     return saved;
 }

 public Ticket requestPolicyChange(String raisedBy, PolicyChangeRequest req) {
     PolicyDto policy = policyClient.getPolicyById(req.getPolicyId());
     if (policy == null) throw new NotFoundException("Policy not found: " + req.getPolicyId());

     Ticket t = new Ticket();
     t.setRaisedBy(raisedBy == null ? "anonymous" : raisedBy);
     t.setPolicyId(req.getPolicyId());
     t.setType(TicketType.POLICY_CHANGE);
     t.setSubject("Policy change: " + req.getChangeType());
     t.setDescription(req.getDetails());
     t.setCreatedAt(OffsetDateTime.now());
     t.setUpdatedAt(OffsetDateTime.now());

     Ticket saved = ticketRepo.save(t);

     // Publish both ticket-created and policy-change-requested
     eventProducer.publishTicketCreated(new TicketCreatedEvent(
             saved.getId(),
             saved.getType().name(),
             saved.getSubject(),
             saved.getDescription(),
             saved.getPolicyId(),
             saved.getRaisedBy(),
             saved.getCreatedAt()
     ));

     eventProducer.publishPolicyChangeRequested(new PolicyChangeRequestedEvent(
             saved.getId(),
             req.getPolicyId(),
             req.getChangeType(),
             req.getDetails(),
             saved.getRaisedBy(),
             saved.getCreatedAt()
     ));

     return saved;
 }

// // Raise an issue ticket
// public Ticket raiseIssue(String raisedBy, Long policyId, String subject, String description) {
//     Ticket t = new Ticket();
//     t.setRaisedBy(raisedBy == null ? "anonymous" : raisedBy);
//     t.setPolicyId(policyId);
//     t.setType(TicketType.ISSUE);
//     t.setSubject(subject);
//     t.setDescription(description);
//     t.setCreatedAt(OffsetDateTime.now());
//     t.setUpdatedAt(OffsetDateTime.now());
//     return ticketRepo.save(t);
// }
//
//
////Publish event
//      eventProducer.publishTicketCreated(new TicketCreatedEvent(
//              saved.getId(),
//              saved.getType().name(),
//              saved.getSubject(),
//              saved.getDescription(),
//              saved.getPolicyId(),
//              saved.getRaisedBy(),
//              saved.getCreatedAt()
//      ));
//
//      return saved;
//  }
//
// 
// 
// // Request a policy change (stored as ticket)
// public Ticket requestPolicyChange(String raisedBy, PolicyChangeRequest req) {
//     // Validate policy exists
//     PolicyDto policy = policyClient.getPolicyById(req.getPolicyId());
//     if (policy == null) {
//         throw new NotFoundException("Policy not found: " + req.getPolicyId());
//     }
//     Ticket t = new Ticket();
//     t.setRaisedBy(raisedBy == null ? "anonymous" : raisedBy);
//     t.setPolicyId(req.getPolicyId());
//     t.setType(TicketType.POLICY_CHANGE);
//     t.setSubject("Policy change: " + req.getChangeType());
//     t.setDescription(req.getDetails());
//     t.setCreatedAt(OffsetDateTime.now());
//     t.setUpdatedAt(OffsetDateTime.now());
//     return ticketRepo.save(t);
// }

 public Ticket getTicket(Long id) {
     return ticketRepo.findById(id).orElseThrow(() -> new NotFoundException("Ticket not found: " + id));
 }

 public List<Ticket> ticketsByUser(String raisedBy) {
     return ticketRepo.findByRaisedBy(raisedBy);
 }

 public List<Ticket> ticketsByPolicy(Long policyId) {
     return ticketRepo.findByPolicyId(policyId);
 }
}