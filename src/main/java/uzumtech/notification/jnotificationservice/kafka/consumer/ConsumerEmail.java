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

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ConsumerEmail {

    NotificationService notificationService;
    EmailService emailService;

    @KafkaListener(
            topics = "email-notifications",
            groupId = "notification-service",
            containerFactory = "emailKafkaListenerContainerFactory"

    )
    public void receiveEmail(NotificationEvent notificationEvent) {
        try {
            emailService.sendEmail(notificationEvent);
            log.info("Получили EMAIL: {}", notificationEvent.content());
        } catch (Exception e) {
            notificationService.updateStatus(notificationEvent.notificationId(), Status.FAILED);
        }
    }
}
