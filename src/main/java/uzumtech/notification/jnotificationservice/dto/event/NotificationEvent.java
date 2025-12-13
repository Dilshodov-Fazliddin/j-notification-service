package uzumtech.notification.jnotificationservice.dto.event;

import lombok.Builder;
import uzumtech.notification.jnotificationservice.constant.enums.NotificationType;

@Builder
public record NotificationEvent(Long notificationId,
                                NotificationType notificationType,
                                String recipient,
                                String content) {

}
