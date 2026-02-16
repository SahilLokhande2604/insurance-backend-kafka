package com.customersupport.dto;

//src/main/java/com/insurance/support/dto/PolicyDto.java

import lombok.Data;

@Data
public class PolicyDto {
 private Long id;
 private String policyNumber;
 private String policyType;
 private Double coverageAmount;
 private Double premiumAmount;
 private String status;
 private Long userId;
}