package uzumtech.notification.jnotificationservice.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private Long merchantId;
    private String content;
    private String receiver;
    private String type;
    private String status;
    private String createdAt;
}
