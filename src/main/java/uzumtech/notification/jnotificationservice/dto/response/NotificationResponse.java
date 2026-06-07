package uzumtech.notification.jnotificationservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {
    Long id;
    Long merchantId;
    String content;
    String receiver;
    String type;
    String status;
    String createdAt;
}
