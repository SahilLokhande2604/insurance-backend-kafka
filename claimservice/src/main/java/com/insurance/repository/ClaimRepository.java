//package com.insurance.repository;
//
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.insurance.model.Claim;
////
////@Repository
////public interface ClaimRepository extends JpaRepository<Claim, Long>{
//////	List<Claim> findByUserId(Long userId);
////	List<Claim> findByCustomerId(Long customerId);
////	
////	
////	
////
////}
//@Repository
//public interface ClaimRepository extends JpaRepository<Claim, Long> {
//    List<Claim> findByCustomerUsername(String customerUsername);
//    void deleteByClaimIdAndCustomerUsername(Long claimId, String customerUsername);
//}

package com.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insurance.model.Claim;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByCustomerUsername(String username);

	List<Claim> findByCustomerId(Long userId);
}
