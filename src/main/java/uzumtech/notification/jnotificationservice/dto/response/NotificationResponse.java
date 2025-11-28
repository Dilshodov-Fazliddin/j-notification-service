package uzumtech.notification.jnotificationservice.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    @NotBlank(message = "id is blank")
    private Long id;

    @NotBlank(message = "merchant id is blank")
    private Long merchantId;

    @NotBlank(message = "content is blank")
    private String content;

    @NotBlank(message = "receiver is blank")
    private String receiver;

    @NotBlank(message = "type is blank")
    private String type;

    @NotBlank(message = "status is blank")
    private String status;

    @NotBlank(message = "createdAt is blank")
    private String createdAt;
}
