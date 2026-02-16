package com.customersupport.client;

//src/main/java/com/insurance/support/client/PolicyClient.java

import com.customersupport.config.AppProperties;
import com.customersupport.dto.PolicyDto;
import com.customersupport.exception.DownstreamServiceException;
import com.customersupport.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PolicyClient {

 private final RestTemplate restTemplate;
 private final AppProperties props;

 public List<PolicyDto> getAllPolicies() {
     String url = props.getPolicy().getBaseUrl();
     try {
         ResponseEntity<List<PolicyDto>> resp = restTemplate.exchange(
                 url, HttpMethod.GET, null,
                 new ParameterizedTypeReference<List<PolicyDto>>() {});
         return resp.getBody();
     } catch (HttpStatusCodeException e) {
         throw new DownstreamServiceException("Policy list error: " + e.getResponseBodyAsString(), e.getStatusCode());
     }
 }

 public List<PolicyDto> getPoliciesByUserId(Long userId) {
     String url = props.getPolicy().getBaseUrl() + "/user/" + userId;
     try {
         ResponseEntity<List<PolicyDto>> resp = restTemplate.exchange(
                 url, HttpMethod.GET, null,
                 new ParameterizedTypeReference<List<PolicyDto>>() {});
         return resp.getBody();
     } catch (HttpStatusCodeException e) {
         throw new DownstreamServiceException("Policy by user error: " + e.getResponseBodyAsString(), e.getStatusCode());
     }
 }

 public PolicyDto getPolicyById(Long id) {
     String url = props.getPolicy().getBaseUrl() + "/" + id;
     try {
         ResponseEntity<PolicyDto> resp = restTemplate.getForEntity(url, PolicyDto.class);
         if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
             return resp.getBody();
         }
         throw new NotFoundException("Policy not found: " + id);
     } catch (HttpStatusCodeException e) {
         if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
             throw new NotFoundException("Policy not found: " + id);
         }
         throw new DownstreamServiceException("Policy get error: " + e.getResponseBodyAsString(), e.getStatusCode());
     }
 }
}