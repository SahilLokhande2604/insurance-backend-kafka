package notification_service.services;

import notification_service.models.Notification;
import notification_service.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // Topics for different services
    private static final String NOTIFICATION_TOPIC = "notification_topic";
    private static final String POLICY_TOPIC = "policy_topic";
    private static final String CLAIM_TOPIC = "claim_topic";
    private static final String PAYMENT_TOPIC = "payment_topic";
    private static final String USER_TOPIC = "user_topic";


    // Method to produce a message to Kafka (generic notification)
    public Notification sendNotification(Notification notification) {
        notification.setStatus("pending");
        Notification saved = notificationRepository.save(notification);
        kafkaTemplate.send(NOTIFICATION_TOPIC, saved);
        return saved;
    }


    // Kafka Listener: Consumes notifications from NOTIFICATION_TOPIC
    @KafkaListener(topics = NOTIFICATION_TOPIC, groupId = "notification-service-group")
    public void consumeNotification(Notification notification) {
        System.out.println("Consuming notification: " + notification.getMessage());
        notification.setStatus("sent");
        notificationRepository.save(notification);
    }

    // Kafka Listener: Consumes policy events
    @KafkaListener(topics = POLICY_TOPIC, groupId = "notification-service-group")
    public void consumePolicyEvent(String message) {
        System.out.println("Received policy event: " + message);
        Notification notification = new Notification();
        notification.setMessage("Policy event: " + message);
        notification.setStatus("pending");
        notificationRepository.save(notification);
    }

    // Kafka Listener: Consumes claim events
    @KafkaListener(topics = CLAIM_TOPIC, groupId = "notification-service-group")
    public void consumeClaimEvent(String message) {
        System.out.println("Received claim event: " + message);
        Notification notification = new Notification();
        notification.setMessage("Claim event: " + message);
        notification.setStatus("pending");
        notificationRepository.save(notification);
    }

    // Kafka Listener: Consumes payment events
    @KafkaListener(topics = PAYMENT_TOPIC, groupId = "notification-service-group")
    public void consumePaymentEvent(String message) {
        System.out.println("Received payment event: " + message);
        Notification notification = new Notification();
        notification.setMessage("Payment event: " + message);
        notification.setStatus("pending");
        notificationRepository.save(notification);
    }

    // Kafka Listener: Consumes user events
    @KafkaListener(topics = USER_TOPIC, groupId = "notification-service-group")
    public void consumeUserEvent(String message) {
        System.out.println("Received user event: " + message);
        Notification notification = new Notification();
        notification.setMessage("User event: " + message);
        notification.setStatus("pending");
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}