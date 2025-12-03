package uzumtech.notification.jnotificationservice.service;

import uzumtech.notification.jnotificationservice.dto.event.NotificationEvent;

public interface EmailService {

    void sendEmail(NotificationEvent notificationEvent);
}
