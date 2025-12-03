package uzumtech.notification.jnotificationservice.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.dto.event.NotificationEvent;
@Slf4j
@Service
public class ConsumerEmail {
    @KafkaListener(
            topics = "email-notifications",
            groupId = "notification-service",
            containerFactory = "emailKafkaListenerContainerFactory"

    )
    public void receiveEmail(NotificationEvent notificationEvent) {
        log.info("Получили EMAIL: {}", notificationEvent.content());
    }
}
