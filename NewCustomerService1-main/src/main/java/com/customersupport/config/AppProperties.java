package com.customersupport.config;

//src/main/java/com/insurance/support/config/AppProperties.java

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "services")
public class AppProperties {
 private Service policy;

 @Data
 public static class Service {
     private String baseUrl;
     private int connectTimeoutMs = 3000;
     private int readTimeoutMs = 5000;
 }
}