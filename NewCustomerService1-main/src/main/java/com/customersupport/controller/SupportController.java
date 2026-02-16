package com.customersupport.controller;

//src/main/java/com/insurance/support/controller/SupportController.java
import com.customersupport.dto.ApiResponse;
import com.customersupport.dto.PolicyChangeRequest;
import com.customersupport.dto.PolicyDto;
import com.customersupport.model.Ticket;
import com.customersupport.service.SupportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Minimal endpoints:
* - Browse all policies
* - View policies for a user
* - Get policy by id
* - Raise issue ticket
* - Request policy change
*/
@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class SupportController {

 private final SupportService service;

 // ----- Policies -----
 @GetMapping("/policies")
 public ResponseEntity<List<PolicyDto>> listPolicies(
         @RequestParam(value = "userId", required = false) Long userId) {
     if (userId != null) return ResponseEntity.ok(service.listPoliciesByUser(userId));
     return ResponseEntity.ok(service.listAllPolicies());
 }

 @GetMapping("/policies/{id}")
 public ResponseEntity<PolicyDto> getPolicy(@PathVariable Long id) {
     return ResponseEntity.ok(service.getPolicy(id));
 }

 // ----- Tickets: Issues -----
 @PostMapping("/tickets/issue")
 public ResponseEntity<Ticket> raiseIssue(
         @RequestHeader(value = "X-User", required = false) String raisedBy,
         @RequestParam(value = "policyId", required = false) Long policyId,
         @RequestParam("subject") String subject,
         @RequestParam("description") String description) {
     Ticket t = service.raiseIssue(raisedBy, policyId, subject, description);
     return ResponseEntity.ok(t);
 }

 // ----- Tickets: Policy Change -----
 @PostMapping("/tickets/policy-change")
 public ResponseEntity<ApiResponse<Ticket>> requestChange(
         @RequestHeader(value = "X-User", required = false) String raisedBy,
         @Valid @RequestBody PolicyChangeRequest req) {
     Ticket t = service.requestPolicyChange(raisedBy, req);
     return ResponseEntity.ok(new ApiResponse<>("Policy change request submitted", t));
 }

 // ----- Ticket retrieval -----
 @GetMapping("/tickets/{id}")
 public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
     return ResponseEntity.ok(service.getTicket(id));
 }

 @GetMapping("/tickets")
 public ResponseEntity<List<Ticket>> listTickets(
         @RequestParam(value = "raisedBy", required = false) String raisedBy,
         @RequestParam(value = "policyId", required = false) Long policyId) {
     if (raisedBy != null) return ResponseEntity.ok(service.ticketsByUser(raisedBy));
     if (policyId != null) return ResponseEntity.ok(service.ticketsByPolicy(policyId));
     // If no filter provided, return empty to avoid dumping all tickets
     return ResponseEntity.ok(List.of());
 }
}
