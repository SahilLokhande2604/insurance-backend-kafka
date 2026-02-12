package notification_service.services;

import notification_service.models.Notification;
import notification_service.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification sendNotification(Notification notification) {
        // For demo: mark as "SENT" and save.
        notification.setStatus("SENT");
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}