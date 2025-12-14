package uzumtech.notification.jnotificationservice.service;


import uzumtech.notification.jnotificationservice.entity.NotificationEntity;

public interface WebhookService {
    void sendWebhook(NotificationEntity notification);
}
