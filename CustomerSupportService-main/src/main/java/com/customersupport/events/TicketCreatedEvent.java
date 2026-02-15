package com.customersupport.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreatedEvent {
    private Long ticketId;
    private String type;        // ISSUE or POLICY_CHANGE
    private String subject;
    private String description;
    private Long policyId;      // nullable
    private String raisedBy;
    private OffsetDateTime createdAt;
}