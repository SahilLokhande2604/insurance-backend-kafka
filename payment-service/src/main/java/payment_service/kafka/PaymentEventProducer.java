package payment_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProducer {
    private static final String PAYMENT_TOPIC = "payment_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendPaymentSuccessEvent(String message) {
        kafkaTemplate.send(PAYMENT_TOPIC, message);
    }

    public void sendPaymentFailureEvent(String message) {
        kafkaTemplate.send(PAYMENT_TOPIC, message);
    }
}
