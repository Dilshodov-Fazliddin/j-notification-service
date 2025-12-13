package uzumtech.notification.jnotificationservice.service;

import uzumtech.notification.jnotificationservice.constant.enums.Status;
import uzumtech.notification.jnotificationservice.dto.request.NotificationEmailRequest;
import uzumtech.notification.jnotificationservice.dto.request.NotificationSmsRequest;
import uzumtech.notification.jnotificationservice.dto.response.NotificationResponse;

public interface NotificationService {
    NotificationResponse sendEmail(NotificationEmailRequest notificationEmailRequest,Long merchantId);
    NotificationResponse sendSms(NotificationSmsRequest smsRequest,Long merchantId);
    void updateStatus(Long notificationId, Status status);

}
