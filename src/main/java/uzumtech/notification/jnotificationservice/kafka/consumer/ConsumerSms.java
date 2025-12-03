package uzumtech.notification.jnotificationservice.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.dto.event.NotificationEvent;

@Slf4j
@Service
public class ConsumerSms {
    @KafkaListener(
            topics = "sms-notifications",
            groupId = "notification-service",
            containerFactory = "smsKafkaListenerContainerFactory"
    )
    public void receiveSms(NotificationEvent notificationEvent) {
        log.info("Получили SMS: {}", notificationEvent.content());
    }
}
