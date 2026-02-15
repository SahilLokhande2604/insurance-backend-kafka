package com.customersupport.dto;

//src/main/java/com/insurance/support/dto/ApiResponse.java
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
 private String message;
 private T data;
}