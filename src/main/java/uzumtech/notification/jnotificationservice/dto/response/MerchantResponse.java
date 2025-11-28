package uzumtech.notification.jnotificationservice.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantResponse {
    private Long id;
    private String companyName;
    private String taxNumber;
    private String login;
    private String webhook;
}
