package com.customersupport.model;

//src/main/java/com/insurance/support/model/Ticket.java

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name = "support_tickets")
@Data
public class Ticket {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 /** Who raised the ticket (username/email/userId string) */
 private String raisedBy;

 /** Link to policy if applicable */
 private Long policyId;

 @Enumerated(EnumType.STRING)
 private TicketType type; // ISSUE or POLICY_CHANGE

 @NotBlank
 private String subject;

 @Column(length = 4000)
 private String description;

 private OffsetDateTime createdAt;
 private OffsetDateTime updatedAt;
}
