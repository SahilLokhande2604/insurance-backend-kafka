//package claim_service.kafka;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ClaimEventProducer {
//    private static final String CLAIM_TOPIC = "claim_topic";
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    public void sendClaimSubmittedEvent(String message) {
//        kafkaTemplate.send(CLAIM_TOPIC, message);
//    }
//
//    public void sendClaimStatusEvent(String message) {
//        kafkaTemplate.send(CLAIM_TOPIC, message);
//    }
//}
