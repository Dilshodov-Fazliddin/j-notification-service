package uzumtech.notification.jnotificationservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uzumtech.notification.jnotificationservice.dto.request.MerchantRequest;
import uzumtech.notification.jnotificationservice.dto.response.MerchantResponse;
import uzumtech.notification.jnotificationservice.service.MerchantService;

@RestController
@RequestMapping("/api/j-notification")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MerchantController {
    MerchantService merchantService;

    @PostMapping("/registration")
    public MerchantResponse registration(@Valid @RequestBody MerchantRequest merchantRequest) {
        return merchantService.create(merchantRequest);
    }
}
