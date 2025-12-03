package uzumtech.notification.jnotificationservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.dto.event.NotificationEvent;
import uzumtech.notification.jnotificationservice.service.SmsService;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SmsServiceImpl implements SmsService {

    @Override
    @Transactional
    public void sendSms(NotificationEvent notificationEvent) {
        log.info("Sent sms to {} content {}", notificationEvent.recipient(),notificationEvent.content());
    }
}
