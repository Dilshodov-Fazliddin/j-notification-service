package uzumtech.notification.jnotificationservice.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantResponse {
    @NotBlank(message = "id is blank")
    private Long id;

    @NotBlank(message = "company is blank")
    private String companyName;

    @NotBlank(message = "taxNumber is blank")
    private String taxNumber;

    @NotBlank(message = "login is blank")
    private String login;

    @NotBlank(message = "webhook is blank")
    private String webhook;
}
