package com.customersupport.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Example structure for events coming from Policy Service (or elsewhere).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyUpdatedEvent {
    private Long policyId;
    private String status; // e.g., ACTIVE, LAPSED, UPDATED
    private Map<String, Object> updatedFields; // e.g., {"address": "..."}
    private OffsetDateTime updatedAt;
}