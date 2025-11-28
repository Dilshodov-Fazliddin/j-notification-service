package uzumtech.notification.jnotificationservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantRequest{
    @NotBlank(message = "company name is blank")
    private String companyName;
    @NotBlank(message = "tax number is blank")
    private String taxNumber;
    @NotBlank(message = "login is blank")
    private String login;
    @NotBlank(message = "webhook is blank")
    private String webhook;
}
