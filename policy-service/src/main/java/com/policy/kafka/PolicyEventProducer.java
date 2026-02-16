//package com.policy.kafka;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PolicyEventProducer {
//    private static final String POLICY_TOPIC = "policy_topic";
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
////    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    public PolicyEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//
//    public void sendPolicyPurchaseEvent(String message) {
//        kafkaTemplate.send(POLICY_TOPIC, message);
//    }
//
//    public void sendPolicyUpdateEvent(String message) {
//        kafkaTemplate.send(POLICY_TOPIC, message);
//    }
//}
