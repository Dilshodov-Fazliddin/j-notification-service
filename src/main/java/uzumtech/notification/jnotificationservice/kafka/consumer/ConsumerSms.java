package uzumtech.notification.jnotificationservice.kafka.consumer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.constant.enums.Status;
import uzumtech.notification.jnotificationservice.dto.event.NotificationEvent;
import uzumtech.notification.jnotificationservice.service.NotificationService;
import uzumtech.notification.jnotificationservice.service.SmsService;
import uzumtech.notification.jnotificationservice.service.WebhookService;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ConsumerSms {

    SmsService smsService;
    NotificationService notificationService;
    WebhookService webhookService;

    @KafkaListener(
            topics = "sms-notifications",
            groupId = "notification-service",
            containerFactory = "smsKafkaListenerContainerFactory"
    )
    public void receiveSms(NotificationEvent notificationEvent) {
        try {
            smsService.sendSms(notificationEvent);
            notificationService.updateStatus(notificationEvent.notificationId(), Status.SENT);
            webhookService.sendWebhook(notificationService.getNotificationById(notificationEvent.notificationId()));

            log.info("SMS sent: {}", notificationEvent.recipient());

        }catch (Exception e){
            notificationService.updateStatus(notificationEvent.notificationId(), Status.FAILED);
            log.error("Failed to send status notification to {}: {}", notificationEvent.recipient(), e.getMessage());
        }
    }
}
