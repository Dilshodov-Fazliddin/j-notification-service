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
public class MerchantResponse {
    @NotBlank(message = "id is blank")
     Long id;

    @NotBlank(message = "company is blank")
     String companyName;

    @NotBlank(message = "taxNumber is blank")
     String taxNumber;

    @NotBlank(message = "login is blank")
     String login;

    @NotBlank(message = "webhook is blank")
     String webhook;
}
