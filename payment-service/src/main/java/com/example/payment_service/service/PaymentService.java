//package com.example.payment_service.service;
//
//
//import com.example.payment_service.client.OrderClient;
//import com.example.payment_service.dto.*;
//import com.example.payment_service.entity.Payment;
//import com.example.payment_service.repository.PaymentRepository;
//import com.razorpay.*;
//import org.apache.commons.codec.digest.HmacUtils;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
// 
//import java.time.LocalDateTime;
// 
//@Service
//public class PaymentService {
// 
//    private  RazorpayClient razorpayClient;
//    private  PaymentRepository paymentRepository;
//    private  OrderClient orderClient;
// 
//    @Value("${razorpay.secret}")
//    private String secret;
//    
//    public PaymentService(RazorpayClient razorpayClient,
//            PaymentRepository paymentRepository,
//            OrderClient orderClient) {
//this.razorpayClient = razorpayClient;
//this.paymentRepository = paymentRepository;
//this.orderClient = orderClient;
//}
// 
//    public CreatePaymentResponse createPayment(CreatePaymentRequest request)
//            throws RazorpayException {
// 
//        JSONObject options = new JSONObject();
//        options.put("amount", (int)(request.getAmount() * 100));
//        options.put("currency", "INR");
// 
//        Order razorpayOrder = razorpayClient.orders.create(options);
// 
////        Payment payment = Payment.builder()
////                .orderId(request.getOrderId())
////                .userId(request.getUserId())
////                .amount(request.getAmount())
////                .razorpayOrderId(razorpayOrder.get("id"))
////                .status("PENDING")
////                .createdAt(LocalDateTime.now())
////                .updatedAt(LocalDateTime.now())
////                .build();
//// 
//        
//        Payment payment = new Payment();
//
//        payment.setOrderId(request.getOrderId());
//        payment.setUserId(request.getUserId());
//        payment.setAmount(request.getAmount());
//        payment.setRazorpayOrderId(razorpayOrder.get("id").toString());
//        payment.setStatus("PENDING");
//        payment.setCreatedAt(LocalDateTime.now());
//        payment.setUpdatedAt(LocalDateTime.now());
//
//        paymentRepository.save(payment);
// 
//        return new CreatePaymentResponse(
//                razorpayOrder.get("id"),
//                request.getAmount(),
//                "INR"
//        );
//    }
// 
////    public String verifyPayment(VerifyPaymentRequest request) {
//// 
////        String payload =
////                request.getRazorpayOrderId() + "|" +
////                request.getRazorpayPaymentId();
//// 
////        String generatedSignature =
////                HmacUtils.hmacSha256Hex(secret, payload);
//// 
////        if (!generatedSignature.equals(request.getSignature())) {
////            throw new RuntimeException("Payment verification failed");
////        }
//// 
////        Payment payment = paymentRepository
////                .findByRazorpayOrderId(request.getRazorpayOrderId())
////                .orElseThrow();
//// 
////        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
////        payment.setStatus("SUCCESS");
////        payment.setUpdatedAt(LocalDateTime.now());
//// 
////        paymentRepository.save(payment);
//// 
////        orderClient.updateOrderStatus(payment.getOrderId(), "CONFIRMED");
//// 
////        return "Payment verified successfully";
////    }
//    
////    public String verifyPayment(VerifyPaymentRequest request) {
////
////        String payload =
////                request.getRazorpayOrderId() + "|" +
////                request.getRazorpayPaymentId();
////
////        String generatedSignature = new HmacUtils("HmacSHA256", secret)
////                .hmacHex(payload);
////
////        if (!generatedSignature.equals(request.getSignature())) {
////            throw new RuntimeException("Payment verification failed");
////        }
////
////        Payment payment = paymentRepository
////                .findByRazorpayOrderId(request.getRazorpayOrderId())
////                .orElseThrow();
////
////        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
////        payment.setStatus("SUCCESS");
////        payment.setUpdatedAt(LocalDateTime.now());
////
////        paymentRepository.save(payment);
////
////        orderClient.updateOrderStatus(payment.getOrderId(), "CONFIRMED");
////
////        return "Payment verified successfully";
////    }
//    
//    public String verifyPayment(VerifyPaymentRequest request) {
//
//        try {
//
//            System.out.println("Razorpay Order ID: " + request.getRazorpayOrderId());
//            System.out.println("Razorpay Payment ID: " + request.getRazorpayPaymentId());
//            System.out.println("Razorpay Signature: " + request.getSignature());
//
//            String payload = request.getRazorpayOrderId() + "|" +
//                             request.getRazorpayPaymentId();
//
//            System.out.println("Payload: " + payload);
//
//            String generatedSignature = new HmacUtils("HmacSHA256", secret)
//                    .hmacHex(payload);
//
//            System.out.println("Generated Signature: " + generatedSignature);
//
//            if (!generatedSignature.equals(request.getSignature())) {
//                throw new RuntimeException("Payment verification failed - Signature mismatch");
//            }
//
//            Payment payment = paymentRepository
//                    .findByRazorpayOrderId(request.getRazorpayOrderId())
//                    .orElseThrow(() -> new RuntimeException("Payment not found"));
//
//            payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
//            payment.setStatus("SUCCESS");
//            payment.setUpdatedAt(LocalDateTime.now());
//
//            paymentRepository.save(payment);
//
//            orderClient.updateOrderStatus(payment.getOrderId(), "CONFIRMED");
//
//            return "Payment verified successfully";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Verification Error: " + e.getMessage());
//        }
//    }
//
//
//}
//
// 
package com.example.payment_service.service;

import com.example.payment_service.dto.*;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.repository.PaymentRepository;
import payment_service.kafka.PaymentEventProducer;
import com.razorpay.*;
import org.apache.commons.codec.digest.HmacUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    private RazorpayClient razorpayClient;
    private PaymentRepository paymentRepository;
    private RestTemplate restTemplate;
    private final PaymentEventProducer paymentEventProducer;

    @Value("${razorpay.secret}")
    private String secret;

    public PaymentService(RazorpayClient razorpayClient,
            PaymentRepository paymentRepository,
            PaymentEventProducer paymentEventProducer) {
        this.razorpayClient = razorpayClient;
        this.paymentRepository = paymentRepository;
        this.paymentEventProducer = paymentEventProducer;
        this.restTemplate = new RestTemplate();
    }

    // ================= CREATE PAYMENT =================

    public CreatePaymentResponse createPayment(CreatePaymentRequest request)
            throws RazorpayException {

        JSONObject options = new JSONObject();
        options.put("amount", (int) (request.getAmount() * 100));
        options.put("currency", "INR");

        Order razorpayOrder = razorpayClient.orders.create(options);

        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setRazorpayOrderId(razorpayOrder.get("id").toString());
        payment.setStatus("PENDING");
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        return new CreatePaymentResponse(
                razorpayOrder.get("id").toString(),
                request.getAmount(),
                "INR"
        );
    }

    // ================= VERIFY PAYMENT =================

    public String verifyPayment(VerifyPaymentRequest request) {

        try {

            System.out.println("Razorpay Order ID: " + request.getRazorpayOrderId());
            System.out.println("Razorpay Payment ID: " + request.getRazorpayPaymentId());
            System.out.println("Razorpay Signature: " + request.getSignature());

            String payload = request.getRazorpayOrderId() + "|" +
                             request.getRazorpayPaymentId();

            String generatedSignature = new HmacUtils("HmacSHA256", secret)
                    .hmacHex(payload);

            System.out.println("Generated Signature: " + generatedSignature);

            if (!generatedSignature.equals(request.getSignature())) {
                throw new RuntimeException("Payment verification failed - Signature mismatch");
            }

            // Fetch payment from DB
            Payment payment = paymentRepository
                    .findByRazorpayOrderId(request.getRazorpayOrderId())
                    .orElseThrow(() -> new RuntimeException("Payment not found"));

            payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
            payment.setStatus("SUCCESS");
            payment.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(payment);
            paymentEventProducer.sendPaymentSuccessEvent("Payment successful: Payment ID " + payment.getPaymentId());

            // ================= CALL POLICY SERVICE =================

            try {

                Map<String, Object> policyRequest = new HashMap<>();
                policyRequest.put("userId", payment.getUserId());
                policyRequest.put("amount", payment.getAmount());
                policyRequest.put("paymentStatus", payment.getStatus());
                policyRequest.put("paymentId", payment.getPaymentId());

                System.out.println("Sending to Policy Service: " + policyRequest);

                restTemplate.postForObject(
                        "http://localhost:8083/api/policies/generate",
                        policyRequest,
                        String.class
                );

            } catch (Exception ex) {
                System.out.println("Policy Service Error: " + ex.getMessage());
                // DO NOT throw exception
            }

            return "Payment verified successfully";

        } catch (Exception e) {
            e.printStackTrace();
            paymentEventProducer.sendPaymentFailureEvent("Payment failed: " + e.getMessage());
            throw new RuntimeException("Verification Error: " + e.getMessage());
        }
    }
}
