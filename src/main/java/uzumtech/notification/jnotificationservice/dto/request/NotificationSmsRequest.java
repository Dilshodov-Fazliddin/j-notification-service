package uzumtech.notification.jnotificationservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSmsRequest {

    @NotBlank(message = "content is empty")
    @Size(max = 300)
    private String content;

    @Pattern(regexp = "^998\\d{9}$")
    @NotBlank(message = "receiver is empty")
    private String receiver;
}
