//////package com.insurance.controller;
//////
//////import java.util.List;
//////import java.util.Optional;
//////
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.web.bind.annotation.GetMapping;
//////import org.springframework.web.bind.annotation.PathVariable;
//////import org.springframework.web.bind.annotation.PostMapping;
//////import org.springframework.web.bind.annotation.RequestBody;
//////import org.springframework.web.bind.annotation.RequestMapping;
//////import org.springframework.web.bind.annotation.RestController;
//////
//////import com.insurance.model.Claim;
//////import com.insurance.service.ClaimServiceImplementation;
//////import com.insurance.dto.*;
//////
//////
//////
//////
//////@RestController
//////@RequestMapping("/api/claims")
//////public class ClaimController {
//////
//////	@Autowired
//////	ClaimServiceImplementation claimService;
//////	
//////	
////////	File a Claim
//////	@PostMapping("/addClaim")
//////	public Claim addClaim(@RequestBody Claim request) {
//////		return claimService.addClaim(request);
//////	}
//////	
//////	
////////	Claim Status Tracking
//////	@GetMapping("/claimStatus/{id}")
//////	public String checkClaimStatus(@PathVariable("id") Long claimId) {
//////		return claimService.checkClaimStatus(claimId);
//////	}
//////	
//////	//returns all claims 
//////	@GetMapping("/claimReview/all")
//////	public List<Claim> getAllClaimReview(){
//////		return claimService.getAllClaimReview();
//////	}
//////	
//////	
////////	Claim Review (Admin/Claim Manager)
//////	@PostMapping("/claimReview")
//////	public ClaimResponse claimReview(@RequestBody ClaimRequestDto request){
//////		return claimService.claimReview(request);
//////		
//////	}
//////	
//////
//////
//////}
////
////
//package com.insurance.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import com.insurance.dto.ClaimRequestDto;
//import com.insurance.dto.ClaimResponse;
//import com.insurance.model.Claim;
//import com.insurance.service.ClaimService;
////
////@RestController
////@RequestMapping("/api/claims")
////public class ClaimController {
////
////    @Autowired
////    private ClaimService claimService; // inject interface, not implementation
////
////    @PostMapping("/addClaim")
////    public Claim addClaim(@RequestBody Claim claim) {
////        return claimService.addClaim(claim);
////    }
////
////    @GetMapping("/claimStatus/{id}")
////    public String checkClaimStatus(@PathVariable Long id) {
////        return claimService.checkClaimStatus(id);
////    }
////
////    @GetMapping("/claimReview/all")
////    public List<Claim> getAllClaimReview() {
////        return claimService.getAllClaimReview();
////    }
////
////    @PostMapping("/claimReview")
////    public ClaimResponse claimReview(@RequestBody ClaimRequestDto request) {
////        return claimService.claimReview(request);
////    }
////    
////    @GetMapping("/user/{userId}")
////    public List<Claim> getClaimsByUser(@PathVariable Long userId) {
////        return claimService.getClaimsByUser(userId);
////    }
////    
////    
////
////}
//@RestController
//@RequestMapping("/api/claims")
//public class ClaimController {
//
//    @Autowired
//    private ClaimService claimService;
//
//    @PostMapping("/add")
//    public Claim addClaim(@RequestBody Claim claim) {
//        return claimService.addClaim(claim);
//    }
// 
//    // For Admin: View everything
//    @GetMapping("/admin/all")
//    public List<Claim> getAllClaims() {
//        return claimService.getAllClaims();
//    }
//
//    // For Admin: Process the claim
//    @PostMapping("/admin/review")
//    public ClaimResponse approveOrReject(@RequestBody ClaimRequestDto request) {
//        return claimService.claimReview(request);
//    }
//
//    // For User: View their own claims
//    @GetMapping("/user/{username}")
//    public List<Claim> getMyClaims(@PathVariable String username) {
//        return claimService.getClaimsByUser(username);
//    }
//
//    @DeleteMapping("/user/{username}/{claimId}")
//    public String deleteClaim(@PathVariable String username, @PathVariable Long claimId) {
//        claimService.deleteClaim(claimId, username);
//        return "Claim deleted successfully";
//    }
//}

package com.insurance.controller;

import com.insurance.dto.ClaimResponse;
import com.insurance.model.Claim;
import com.insurance.service.ClaimService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // ================= USER =================
    @PostMapping("/add")
    public Claim addClaim(@RequestBody Claim claim) {
        return claimService.addClaim(claim);
    }

    @GetMapping("/user/{username}")
    public List<Claim> getClaimsByUser(@PathVariable String username) {
        return claimService.getClaimsByUser(username);
    }

    @GetMapping("/user/status/{claimId}")
    public String checkClaimStatus(@PathVariable Long claimId) {
        return claimService.getClaim(claimId)
                .map(Claim::getStatus)
                .orElse("NOT_FOUND");
    }

    // ================= ADMIN =================
    @GetMapping("/admin/all")
    public List<Claim> getAllClaims() {
        return claimService.getAllClaimReview();
    }

    @PostMapping("/admin/review/{claimId}")
    public ClaimResponse reviewClaim(
            @PathVariable Long claimId,
            @RequestParam String action) { // action=APPROVE/REJECT
        return claimService.reviewClaim(action, claimId);
    }
}
