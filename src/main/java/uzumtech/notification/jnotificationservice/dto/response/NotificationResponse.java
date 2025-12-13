package uzumtech.notification.jnotificationservice.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {

    @NotBlank(message = "id is blank")
     Long id;

    @NotBlank(message = "merchant id is blank")
     Long merchantId;

    @NotBlank(message = "content is blank")
     String content;

    @NotBlank(message = "receiver is blank")
     String receiver;

    @NotBlank(message = "type is blank")
     String type;

    @NotBlank(message = "status is blank")
     String status;

    @NotBlank(message = "createdAt is blank")
     String createdAt;
}
