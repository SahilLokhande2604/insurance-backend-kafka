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

    private static final String TOPIC = "notification_topic";

    // Method to produce a message to Kafka
    public Notification sendNotification(Notification notification) {
        notification.setStatus("pending");
        Notification saved = notificationRepository.save(notification);
        kafkaTemplate.send(TOPIC, saved);
        return saved;
    }

    // Kafka Listener: Consumes and updates status to 'sent'
    @KafkaListener(topics = TOPIC, groupId = "notification-service-group")
    public void consumeNotification(Notification notification) {
        System.out.println("Consuming message: " + notification.getMessage());
        notification.setStatus("sent");
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}