////package com.insurance.service;
////
////import java.util.List;
////import java.util.Optional;
////
////import com.insurance.dto.ClaimRequestDto;
////import com.insurance.dto.ClaimResponse;
////import com.insurance.dto.ClaimResponseDto;
////import com.insurance.model.Claim;
////
////public interface ClaimService {
////	
////	public Claim addClaim(Claim request);
////
////	public String checkClaimStatus(Long claimId);
////
////	public List<Claim> getAllClaimReview();
////
////	public Optional<Claim> getClaim(Long claimId);
////	
////	public ClaimResponse claimReview(ClaimRequestDto request);
////
////	
////}
//package com.insurance.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import com.insurance.dto.ClaimRequestDto;
//import com.insurance.dto.ClaimResponse;
//import com.insurance.model.Claim;
//
//public interface ClaimService {
//
//    Claim addClaim(Claim claim);
//
//    String checkClaimStatus(Long claimId);
//
//    Optional<Claim> getClaim(Long claimId);
//
//    List<Claim> getAllClaimReview();
//
//    ClaimResponse claimReview(ClaimRequestDto request);
//
//	List<Claim> getClaimsByUser(Long userId);
//
//	void deleteClaim(Long claimId, String username);
//
//	List<Claim> getClaimsByUser(String username);
//	
//	public List<Claim> getAllClaims();
//    
//    
//}
package com.insurance.service;

import com.insurance.dto.ClaimRequestDto;
import com.insurance.dto.ClaimResponse;
import com.insurance.model.Claim;

import java.util.List;
import java.util.Optional;

public interface ClaimService {
    Claim addClaim(Claim claim);
    List<Claim> getClaimsByUser(String username);
    List<Claim> getAllClaims();
    ClaimResponse reviewClaim(String adminAction, Long claimId);
    Optional<Claim> getClaim(Long claimId);
//	List<Claim> getClaimsByUser(Long userId);
	List<Claim> getAllClaimReview();
	ClaimResponse claimReview(ClaimRequestDto request);
	String checkClaimStatus(Long claimId);
}
