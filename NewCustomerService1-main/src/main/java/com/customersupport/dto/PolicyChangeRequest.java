package com.customersupport.dto;

//src/main/java/com/insurance/support/dto/PolicyChangeRequest.java

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
* Minimal change request—for example change address, coverage, or plan option.
* We'll store it as a Ticket and (optionally) call Policy Service if you later add endpoints for updates.
*/
@Data
public class PolicyChangeRequest {
 @NotNull
 private Long policyId;

 @NotNull
 @Size(min = 3, max = 100)
 private String changeType; // e.g., "ADDRESS_UPDATE", "COVERAGE_UPDATE"

 @NotNull
 @Size(min = 5, max = 2000)
 private String details; // free-form JSON/text explaining change request
}