package uzumtech.notification.jnotificationservice.service;

import uzumtech.notification.jnotificationservice.dto.request.MerchantRequest;
import uzumtech.notification.jnotificationservice.dto.response.MerchantResponse;

public interface MerchantService {
    MerchantResponse create(MerchantRequest request);
}
