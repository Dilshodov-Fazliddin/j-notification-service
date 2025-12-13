package uzumtech.notification.jnotificationservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MerchantRequest{
    @NotBlank(message = "company name is blank")
    String companyName;
    @NotBlank(message = "tax number is blank")
    String taxNumber;
    @NotBlank(message = "login is blank")
    String login;
    @NotBlank(message = "webhook is blank")
    String webhook;
}
