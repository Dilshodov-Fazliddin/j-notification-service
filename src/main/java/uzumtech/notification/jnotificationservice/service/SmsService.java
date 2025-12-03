package uzumtech.notification.jnotificationservice.service;

import uzumtech.notification.jnotificationservice.dto.event.NotificationEvent;

public interface SmsService {
    void sendSms(NotificationEvent notificationEvent);
}
