//package com.insurance.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.insurance.dto.ClaimRequestDto;
//import com.insurance.dto.ClaimResponse;
//import com.insurance.dto.Policy;
//import com.insurance.model.Claim;
//import com.insurance.repository.ClaimRepository;
//
//////
//////import java.time.LocalDate;
//////import java.util.ArrayList;
//////import java.util.List;
//////import java.util.Optional;
//////
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.stereotype.Service;
//////import org.springframework.web.client.HttpStatusCodeException;
//////import org.springframework.web.client.ResourceAccessException;
//////import org.springframework.web.client.RestClientException;
//////import org.springframework.web.client.RestTemplate;
//////
//////import com.insurance.dto.ClaimRequestDto;
//////import com.insurance.dto.ClaimResponse;
//////import com.insurance.dto.Policy;
//////import com.insurance.dto.User;
//////import com.insurance.enums.DownstreamService;
//////import com.insurance.exception.DownstreamUnavailableException;
//////import com.insurance.model.Claim;
//////import com.insurance.repository.ClaimRepository;
//////
//////import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
//////import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//////
//////
//////@Service
//////public class ClaimServiceImplementation implements ClaimService{
//////
//////	@Autowired
//////	ClaimRepository claimRepository;
//////	
//////	@Autowired
//////	RestTemplate restTemplate;
//////	
//////	@Override
//////	public Claim addClaim(Claim request) {
//////		// TODO Auto-generated method stub
//////		return claimRepository.save(request);
//////	}
//////	
//////	@Override
//////	public  String checkClaimStatus(Long claimId) {
//////		Optional<Claim> response = claimRepository.findById(claimId);
//////		Claim claim = response.get();
//////		return claim.getStatus();
//////	}
//////	
//////	@Override
//////	public  Optional<Claim> getClaim(Long claimId) {
//////		return claimRepository.findById(claimId);
//////	}
//////
//////	@Override
//////	public List<Claim> getAllClaimReview() {
//////		return claimRepository.findAll();
//////	}
//////	
//////	
//////	
//////
//////	@Override
//////	@CircuitBreaker(name="claimservice",fallbackMethod = "fallbackmethodforpostRequest")
//////	public ClaimResponse claimReview(ClaimRequestDto request) {
//////		if (request == null) return new ClaimResponse("FAIL", "NULL Request");
//////			Long userId = request.getUser().getId(); 
//////			Long policyId = request.getPolicy().getPolicyId();
//////
//////		User user;
//////		Policy policy;
//////		
//////	    try {
//////	        user = restTemplate.getForObject("http://localhost:8080/api/users/{id}", User.class, userId);
//////	    } catch (HttpStatusCodeException ex) {
//////	        throw new DownstreamUnavailableException(DownstreamService.USER,
//////	            "User service HTTP error: " + ex.getStatusCode().value(), ex, ex.getStatusCode().value());
//////	    } catch (ResourceAccessException ex) { // connection refused / timeout
//////	        throw new DownstreamUnavailableException(DownstreamService.USER,
//////	            "User service unreachable: " + ex.getMessage(), ex, -1);
//////	    } catch (RestClientException ex) {
//////	        throw new DownstreamUnavailableException(DownstreamService.USER,
//////	            "User service error: " + ex.getMessage(), ex, -1);
//////	    }
//////	    
//////
//////		try {
//////		        policy = restTemplate.getForObject("http://localhost:8181/api/policies/user/{userId}", Policy.class, userId);
//////		    } catch (HttpStatusCodeException ex) {
//////		        throw new DownstreamUnavailableException(DownstreamService.POLICY,
//////		            "Policy service HTTP error: " + ex.getStatusCode().value(), ex, ex.getStatusCode().value());
//////		    } catch (ResourceAccessException ex) {
//////		        throw new DownstreamUnavailableException(DownstreamService.POLICY,
//////		            "Policy service unreachable: " + ex.getMessage(), ex, -1);
//////		    } catch (RestClientException ex) {
//////		        throw new DownstreamUnavailableException(DownstreamService.POLICY,
//////		            "Policy service error: " + ex.getMessage(), ex, -1);
//////		    }
//////
//////			Claim claim = request.getClaim();			
//////
//////			List<String> errors = new ArrayList<>();
//////
//////			if (!validateDuration(policy, claim)) {
//////			errors.add("Duration validation failed: policyStartDate=" + policy.getPolicyStartDate()
//////			+ ", claimStartDate=" + claim.getPolicyStartDate());
//////			}
//////			if (!validateCovarageAmount(policy, claim)) {
//////			errors.add("Coverage amount validation failed: policyCoverage=" + policy.getCoverageAmount()
//////			+ ", claimAmount=" + claim.getAmount());
//////			}
////////			if (!validateDocumentDetails(policy, claim, user)) {
////////			errors.add("Document details validation failed: claimType/documentId mismatch with user records");
////////			}
//////			if (!validatePolicyCategory(policy, claim)) {
//////			errors.add("Policy category validation failed: policyCategory=" + policy.getPolicyCategory()
//////			+ ", claimType=" + claim.getClaimType());
//////			}
//////
//////			if (!errors.isEmpty()) {
//////			String errorMsg = "";
//////			for(String error: errors) {
//////				errorMsg+=error+", ";
//////			}
//////			
//////			String existingNote = claim.getNote() == null ? "" : claim.getNote();
//////			claim.setNote((existingNote.isEmpty() ? "" : existingNote + " | ") + "Validation failed: " + errorMsg);
//////			claim.setNote(errorMsg);
//////			claim.setStatus("REJECTED");
//////			}
//////			else {
//////			claim.setStatus("APPROVED");
//////			claim.setNote("Approved Successfully");
//////			}
//////			
//////			Claim saved = claimRepository.save(claim);
//////			
//////			return new ClaimResponse(saved.getStatus(), saved.getNote());
//////	}
//////	
//////	
//////	public boolean validateDuration(Policy policy, Claim claim) {
//////		int flag1= policy.getPolicyStartDate().compareTo(claim.getPolicyStartDate());
//////		int flag2=policy.getPolicyEndDate().compareTo(claim.getPolicyEndDate());
//////		return flag1 == flag2;
//////	}
//////	
//////	public boolean validateCovarageAmount(Policy policy, Claim claim) {
//////		return policy.getCoverageAmount()>=claim.getAmount();
//////	}
//////	
//////	public boolean validateDocumentDetails(Policy policy, Claim claim, User user) {
//////		if(!claim.getDocumentType().equalsIgnoreCase(user.getDocumentType())){
//////			return false;
//////		}
//////		return claim.getDocumentId().equalsIgnoreCase(user.getDocumentId());
//////	}
//////	
//////	
//////	public boolean validatePolicyCategory(Policy policy, Claim claim) {
//////		return policy.getPolicyCategory().equalsIgnoreCase(claim.getClaimType());
//////	}
//////	
//////
//////
//////public ClaimResponse fallbackmethodforpostRequest(ClaimRequestDto request, Throwable throwable) {
//////    String status = "FAIL";
//////    String msg="";
//////
//////    if (throwable instanceof DownstreamUnavailableException due) {
//////        if (due.getService() == DownstreamService.USER) {
//////            msg+= "User Service is unavailable. ";
//////        } if (due.getService() == DownstreamService.POLICY) {
//////            msg+= "Policy Service is unavailable. ";
//////        } 
//////    } else if (throwable instanceof CallNotPermittedException) {
//////        // Circuit is OPEN and short-circuiting
//////        msg+= "Claim Service circuit is open. Please try again after some time. ";
//////    } else {
//////        // Any other unexpected error
//////        msg+= "Claim processing failed. Please try again after some time. ";
//////    }
//////
//////    return new ClaimResponse(status, msg);
//////}
//////
//////
//////	
//////	
//////
//////	
//////}
////
////
////package com.insurance.service;
////
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Optional;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////import org.springframework.web.client.RestClientException;
////import org.springframework.web.client.RestTemplate;
////
////import com.insurance.dto.ClaimRequestDto;
////import com.insurance.dto.ClaimResponse;
////import com.insurance.dto.Policy;
////import com.insurance.dto.User;
////import com.insurance.enums.DownstreamService;
////import com.insurance.exception.DownstreamUnavailableException;
////import com.insurance.model.Claim;
////import com.insurance.repository.ClaimRepository;
////
////import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
////import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
////
////@Service
////public class ClaimServiceImplementation implements ClaimService {
////
////    @Autowired
////    private ClaimRepository claimRepository;
////
////    @Autowired
////    private RestTemplate restTemplate;
////
////    @Override
////    public Claim addClaim(Claim claim) {
////        claim.setStatus("PENDING");
////        return claimRepository.save(claim);
////    }
////
////    @Override
////    public String checkClaimStatus(Long claimId) {
////        return claimRepository.findById(claimId)
////                .map(Claim::getStatus)
////                .orElse("NOT_FOUND");
////    }
////
////    @Override
////    public Optional<Claim> getClaim(Long claimId) {
////        return claimRepository.findById(claimId);
////    }
////
////    @Override
////    public List<Claim> getAllClaimReview() {
////        return claimRepository.findAll();
////    }
////
////    @Override
////    @CircuitBreaker(name = "claimservice", fallbackMethod = "fallbackmethodforpostRequest")
////    public ClaimResponse claimReview(ClaimRequestDto request) {
////        if (request == null) return new ClaimResponse("FAIL", "NULL Request");
////
////        Long userId = request.getUser().getId();
////        User user;
////        Policy policy;
////
////        try {
////            user = restTemplate.getForObject("http://localhost:8080/api/users/{id}", User.class, userId);
////        } catch (RestClientException ex) {
////            throw new DownstreamUnavailableException(DownstreamService.USER, "User service error: " + ex.getMessage(), ex, -1);
////        }
////
////        try {
////            policy = restTemplate.getForObject("http://localhost:8181/api/policies/user/{userId}", Policy.class, userId);
////        } catch (RestClientException ex) {
////            throw new DownstreamUnavailableException(DownstreamService.POLICY, "Policy service error: " + ex.getMessage(), ex, -1);
////        }
////
////        Claim claim = request.getClaim();
////        claim.setCustomerId(userId);
////
////        List<String> errors = new ArrayList<>();
////        if (!validateDuration(policy, claim)) errors.add("Duration validation failed");
////        if (!validateCovarageAmount(policy, claim)) errors.add("Coverage amount validation failed");
////        if (!validatePolicyCategory(policy, claim)) errors.add("Policy category validation failed");
////
////        if (!errors.isEmpty()) {
////            claim.setStatus("REJECTED");
////            claim.setNote(String.join(" | ", errors));
////        } else {
////            claim.setStatus("APPROVED");
////            claim.setNote("Approved Successfully");
////        }
////
////        claimRepository.save(claim);
////        return new ClaimResponse(claim.getStatus(), claim.getNote());
////    }
////
////    private boolean validateDuration(Policy policy, Claim claim) {
////        return !policy.getPolicyStartDate().isAfter(claim.getPolicyStartDate()) &&
////               !policy.getPolicyEndDate().isBefore(claim.getPolicyEndDate());
////    }
////
////    private boolean validateCovarageAmount(Policy policy, Claim claim) {
////        return policy.getCoverageAmount() >= claim.getAmount();
////    }
////
////    private boolean validatePolicyCategory(Policy policy, Claim claim) {
////        return policy.getPolicyCategory().equalsIgnoreCase(claim.getClaimType());
////    }
////
////    public ClaimResponse fallbackmethodforpostRequest(ClaimRequestDto request, Throwable throwable) {
////        String status = "FAIL";
////        String msg = "Claim processing failed.";
////
////        if (throwable instanceof DownstreamUnavailableException due) {
////            if (due.getService() == DownstreamService.USER) msg = "User Service unavailable.";
////            if (due.getService() == DownstreamService.POLICY) msg = "Policy Service unavailable.";
////        } else if (throwable instanceof CallNotPermittedException) {
////            msg = "Circuit is open. Try later.";
////        }
////
////        return new ClaimResponse(status, msg);
////    }
////    
////    @Override
////    public List<Claim> getClaimsByUser(Long userId) {
////        return claimRepository.findByCustomerId(userId);
////    }
////
////}
//
//@Service
//public class ClaimServiceImplementation implements ClaimService {
//
//    @Autowired
//    private ClaimRepository claimRepository;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private static final String POLICY_SERVICE_URL = "http://localhost:8089/api/policies/my";
//
//    @Override
//    public Claim addClaim(Claim claim) {
//        // 1. Check if the policy exists in user's 'my-policy' list via Policy Service
//        try {
//            // Fetch the specific purchased policy for this user
//            Policy purchasedPolicy = restTemplate.getForObject(
//                POLICY_SERVICE_URL + "{username}/{policyId}", 
//                Policy.class, 
//                claim.getCustomerUsername(), 
//                claim.getPolicyId()
//            );
//
//            if (purchasedPolicy == null) {
//                throw new RuntimeException("Policy not found in your account.");
//            }
//
//            // 2. Set initial state
//            claim.setStatus("PENDING");
//            return claimRepository.save(claim);
//        } catch (Exception e) {
//            throw new RuntimeException("Validation failed: User does not own this policy or Service Down.");
//        }
//    }
//
//    @Override
//    public ClaimResponse claimReview(ClaimRequestDto request) {
//        // Admin manually decides the status in the request
//        Claim existingClaim = claimRepository.findById(request.getClaim().getClaimId())
//                .orElseThrow(() -> new RuntimeException("Claim not found"));
//
//        existingClaim.setStatus(request.getClaim().getStatus()); // APPROVED or REJECTED
//        existingClaim.setNote(request.getClaim().getNote());
//        
//        claimRepository.save(existingClaim);
//        return new ClaimResponse(existingClaim.getStatus(), "Claim updated by Admin");
//    }
//
//    @Override
//    public List<Claim> getClaimsByUser(String username) {
//        return claimRepository.findByCustomerUsername(username);
//    }
//
//    @Override
//    public void deleteClaim(Long claimId, String username) {
//        claimRepository.deleteByClaimIdAndCustomerUsername(claimId, username);
//    }
//
//
//    @Override
//    public List<Claim> getAllClaims() {
//        return claimRepository.findAll();
//    }
//
//	@Override
//	public String checkClaimStatus(Long claimId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Optional<Claim> getClaim(Long claimId) {
//		// TODO Auto-generated method stub
//		return Optional.empty();
//	}
//
//	@Override
//	public List<Claim> getAllClaimReview() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	
//
//	@Override
//	public List<Claim> getClaimsByUser(Long userId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
package com.insurance.service;

import com.insurance.dto.ClaimRequestDto;
import com.insurance.dto.ClaimResponse;
import com.insurance.dto.UserPolicy;
import com.insurance.model.Claim;
import com.insurance.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ClaimServiceImplementation implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Claim addClaim(Claim claim) {
        claim.setStatus("PENDING"); // default
        return claimRepository.save(claim);
    }

    @Override
    public String checkClaimStatus(Long claimId) {
        return claimRepository.findById(claimId)
                .map(Claim::getStatus)
                .orElse("NOT_FOUND");
    }

    @Override
    public Optional<Claim> getClaim(Long claimId) {
        return claimRepository.findById(claimId);
    }

    @Override
    public List<Claim> getAllClaimReview() {
        return claimRepository.findAll();
    }

    @Override
    public ClaimResponse claimReview(ClaimRequestDto request) {
        if (request == null || request.getClaim() == null) {
            return new ClaimResponse("FAIL", "Invalid claim request");
        }

        Claim claim = request.getClaim();
        String username = claim.getCustomerUsername();

        // 🔥 Fetch all user policies from My-Policies service
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-USERNAME", username);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserPolicy[]> response = restTemplate.exchange(
                "http://localhost:8181/api/policies/my",
                HttpMethod.GET,
                entity,
                UserPolicy[].class
        );

        UserPolicy[] userPolicies = response.getBody();
        if (userPolicies == null || userPolicies.length == 0) {
            claim.setStatus("REJECTED");
            claim.setNote("No policies found for user.");
            claimRepository.save(claim);
            return new ClaimResponse(claim.getStatus(), claim.getNote());
        }

        // 🔥 Find the purchased policy matching claim.policyId
        Optional<UserPolicy> matchingPolicy = Arrays.stream(userPolicies)
                .filter(up -> up.getPolicy().getPolicyId().equals(claim.getPolicyId())
                        && "ACTIVE".equalsIgnoreCase(up.getStatus()))
                .findFirst();

        if (matchingPolicy.isEmpty()) {
            claim.setStatus("REJECTED");
            claim.setNote("Policy not purchased or inactive.");
            claimRepository.save(claim);
            return new ClaimResponse(claim.getStatus(), claim.getNote());
        }

        // Admin manual approval required
        // Set status to PENDING and include note for admin
        claim.setStatus("PENDING");
        claim.setNote("Pending admin review. User submitted claim for policy: "
                + matchingPolicy.get().getPolicy().getPolicyName());

        // Save claim to DB
        claimRepository.save(claim);

        return new ClaimResponse(claim.getStatus(), claim.getNote());
    }

    @Override
    public List<Claim> getClaimsByUser(String username) {
        return claimRepository.findByCustomerUsername(username);
    }

//	@Override
//	public List<Claim> getClaimsByUser(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Claim> getAllClaims() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClaimResponse reviewClaim(String adminAction, Long claimId) {
		// TODO Auto-generated method stub
		return null;
	}
}
