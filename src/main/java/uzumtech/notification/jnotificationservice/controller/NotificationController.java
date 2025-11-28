package uzumtech.notification.jnotificationservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uzumtech.notification.jnotificationservice.dto.request.MerchantRequest;
import uzumtech.notification.jnotificationservice.dto.request.NotificationEmailRequest;
import uzumtech.notification.jnotificationservice.dto.request.NotificationSmsRequest;
import uzumtech.notification.jnotificationservice.dto.response.MerchantResponse;
import uzumtech.notification.jnotificationservice.dto.response.NotificationResponse;
import uzumtech.notification.jnotificationservice.service.MerchantService;
import uzumtech.notification.jnotificationservice.service.NotificationService;

@RestController
@RequestMapping("/api/j-notification")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    NotificationService notificationService;

    @PostMapping("/sms/{merchantId}")
    public ResponseEntity<NotificationResponse> sendSms(
            @PathVariable Long merchantId,
            @Valid @RequestBody NotificationSmsRequest request
    ) {
        NotificationResponse response = notificationService.sendSms(request, merchantId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/email/{merchantId}")
    public ResponseEntity<NotificationResponse> sendEmail(
            @PathVariable Long merchantId,
            @Valid @RequestBody NotificationEmailRequest request
    ) {
        NotificationResponse response = notificationService.sendEmail(request, merchantId);
        return ResponseEntity.ok(response);
    }
}
