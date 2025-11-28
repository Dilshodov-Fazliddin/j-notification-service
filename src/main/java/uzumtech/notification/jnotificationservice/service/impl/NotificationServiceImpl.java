package uzumtech.notification.jnotificationservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    NotificationRepository notificationRepository;
    MerchantRepository merchantRepository;
    NotificationMapper notificationMapper;


    @Override
    @Transactional
    public NotificationResponse sendEmail(NotificationEmailRequest notificationEmailRequest, Long merchantId) {

        MerchantEntity merchantEntity = merchantRepository
                .findById(merchantId).orElseThrow(() -> new UsernameNotFoundException("Merchant not found " + merchantId));

        NotificationEntity emailNotification = notificationMapper
                .toEmailNotification(notificationEmailRequest, merchantEntity);

        notificationRepository.save(emailNotification);
        log.info("EMAIL notification sent | merchantId={} | phoneNumber={} | message='{}' | notificationId={}",
                merchantId,
                notificationEmailRequest.getEmail(),
                notificationEmailRequest.getContent(),
                emailNotification.getId());

        return notificationMapper.toResponse(emailNotification);
    }

    @Override
    @Transactional
    public NotificationResponse sendSms(NotificationSmsRequest smsRequest, Long merchantId) {
        MerchantEntity merchantEntity = merchantRepository
                .findById(merchantId).orElseThrow(() -> new UsernameNotFoundException("Merchant not found " + merchantId));

        NotificationEntity smsNotification = notificationMapper
                .toSmsNotification(smsRequest, merchantEntity);

        notificationRepository.save(smsNotification);

        log.info("SMS notification sent | merchantId={} | phoneNumber={} | message='{}' | notificationId={}",
                merchantId,
                smsRequest.getReceiver(),
                smsRequest.getContent(),
                smsNotification.getId());

        return notificationMapper.toResponse(smsNotification);
    }
}
