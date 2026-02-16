package user_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {
    private static final String USER_TOPIC = "user_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendUserEvent(String message) {
        kafkaTemplate.send(USER_TOPIC, message);
    }
}
