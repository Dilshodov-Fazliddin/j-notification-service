package uzumtech.notification.jnotificationservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uzumtech.notification.jnotificationservice.dto.webhook.WebhookDto;
import uzumtech.notification.jnotificationservice.entity.NotificationEntity;
import uzumtech.notification.jnotificationservice.service.WebhookService;



@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebhookServiceImpl implements WebhookService {

    WebClient webClient;

    @Override
    public void sendWebhook(NotificationEntity notification) {
        String webhook = notification.getMerchant().getWebhook();

        WebhookDto build = WebhookDto.builder()
                .type(notification.getType().name())
                .status(notification.getStatus().name())
                .notificationId(notification.getId())
                .receiver(notification.getReceiver())
                .build();
        try {
            webClient.post()
                    .uri(webhook)
                    .bodyValue(build)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            log.info(
                    "WEBHOOK_SEND_SUCCESS notificationId={}",
                    notification.getId()
            );

        } catch (Exception e) {
            log.warn(
                    "WEBHOOK_SEND_FAILED notificationId={} error={}",
                    notification.getId(),
                    e.getMessage()
            );
        }
    }
}
