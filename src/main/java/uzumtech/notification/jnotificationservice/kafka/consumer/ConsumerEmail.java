package uzumtech.notification.jnotificationservice.kafka.consumer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.constant.enums.Status;
import uzumtech.notification.jnotificationservice.dto.event.NotificationEvent;
import uzumtech.notification.jnotificationservice.service.EmailService;
import uzumtech.notification.jnotificationservice.service.NotificationService;
import uzumtech.notification.jnotificationservice.service.WebhookService;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ConsumerEmail {

    NotificationService notificationService;
    EmailService emailService;
    WebhookService webhookService;

    @KafkaListener(
            topics = "email-notifications",
            groupId = "notification-service",
            containerFactory = "emailKafkaListenerContainerFactory"

    )
    public void receiveEmail(NotificationEvent notificationEvent) {
        try {
            emailService.sendEmail(notificationEvent);
            notificationService.updateStatus(notificationEvent.notificationId(), Status.SENT);
            webhookService.sendWebhook(notificationService.getNotificationById(notificationEvent.notificationId()));

            log.info("EMAIL sent: {}", notificationEvent.recipient());

        } catch (Exception e) {
            notificationService.updateStatus(notificationEvent.notificationId(), Status.FAILED);
            log.error("Failed to send status notification to {}: {}", notificationEvent.recipient(), e.getMessage());

        }
    }
}
