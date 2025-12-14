package uzumtech.notification.jnotificationservice.dto.webhook;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebhookDto {
    Long notificationId;
    String status;
    String type;
    String receiver;
}
