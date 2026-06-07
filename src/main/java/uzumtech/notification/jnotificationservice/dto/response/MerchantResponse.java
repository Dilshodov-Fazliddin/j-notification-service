package uzumtech.notification.jnotificationservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MerchantResponse {
    Long id;
    String companyName;
    String taxNumber;
    String login;
    String webhook;
}
