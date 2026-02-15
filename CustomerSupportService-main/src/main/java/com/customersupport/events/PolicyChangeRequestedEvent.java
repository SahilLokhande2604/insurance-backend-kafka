package com.customersupport.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyChangeRequestedEvent {
    private Long ticketId;
    private Long policyId;
    private String changeType;  // e.g., ADDRESS_UPDATE, COVERAGE_UPDATE
    private String details;
    private String raisedBy;
    private OffsetDateTime createdAt;
}