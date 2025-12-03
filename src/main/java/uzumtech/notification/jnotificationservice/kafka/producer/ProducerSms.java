package uzumtech.notification.jnotificationservice.kafka.producer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.dto.event.NotificationEvent;
import uzumtech.notification.jnotificationservice.dto.request.NotificationSmsRequest;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
public class ProducerSms {
    KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void send(NotificationEvent notificationEvent) {
        kafkaTemplate.send("sms-notifications",String.valueOf(notificationEvent.notificationId()) ,notificationEvent);
    }
}