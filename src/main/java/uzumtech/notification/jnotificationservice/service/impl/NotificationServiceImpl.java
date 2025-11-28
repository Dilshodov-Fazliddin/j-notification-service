package uzumtech.notification.jnotificationservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.dto.request.NotificationEmailRequest;
import uzumtech.notification.jnotificationservice.dto.request.NotificationSmsRequest;
import uzumtech.notification.jnotificationservice.dto.response.NotificationResponse;
import uzumtech.notification.jnotificationservice.mapper.NotificationMapper;
import uzumtech.notification.jnotificationservice.model.MerchantEntity;
import uzumtech.notification.jnotificationservice.model.NotificationEntity;
import uzumtech.notification.jnotificationservice.repository.MerchantRepository;
import uzumtech.notification.jnotificationservice.repository.NotificationRepository;
import uzumtech.notification.jnotificationservice.service.NotificationService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationServiceImpl implements NotificationService {

    NotificationRepository notificationRepository;
    MerchantRepository merchantRepository;
    NotificationMapper notificationMapper;
    NotificationResponse notificationResponse;


    @Override
    public NotificationResponse sendEmail(NotificationEmailRequest notificationEmailRequest, Long merchantId) {

        MerchantEntity merchantEntity = merchantRepository
                .findById(merchantId).orElseThrow(() -> new UsernameNotFoundException("Merchant not found " + merchantId));

        NotificationEntity emailNotification = notificationMapper
                .toEmailNotification(notificationEmailRequest, merchantEntity);

        notificationRepository.save(emailNotification);

        return notificationMapper.toResponse(emailNotification);
    }

    @Override
    public NotificationResponse sendSms(NotificationSmsRequest smsRequest, Long merchantId) {
        MerchantEntity merchantEntity = merchantRepository
                .findById(merchantId).orElseThrow(() -> new UsernameNotFoundException("Merchant not found " + merchantId));

        NotificationEntity smsNotification = notificationMapper
                .toSmsNotification(smsRequest, merchantEntity);

        notificationRepository.save(smsNotification);
        return notificationMapper.toResponse(smsNotification);
    }
}
