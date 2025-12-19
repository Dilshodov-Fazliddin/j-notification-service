package uzumtech.notification.jnotificationservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationSmsRequest {

    @NotBlank(message = "content is empty")
    String content;

    @Pattern(regexp = "^998\\d{9}$")
    @NotBlank(message = "receiver is empty")
    String receiver;
}
