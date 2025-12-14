package uzumtech.notification.jnotificationservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uzumtech.notification.jnotificationservice.constant.enums.NotificationType;
import uzumtech.notification.jnotificationservice.constant.enums.Status;
import uzumtech.notification.jnotificationservice.dto.event.NotificationEvent;
import uzumtech.notification.jnotificationservice.dto.request.NotificationEmailRequest;
import uzumtech.notification.jnotificationservice.dto.request.NotificationSmsRequest;
import uzumtech.notification.jnotificationservice.dto.response.NotificationResponse;
import uzumtech.notification.jnotificationservice.exception.DataNotFoundException;
import uzumtech.notification.jnotificationservice.kafka.producer.ProducerEmail;
import uzumtech.notification.jnotificationservice.kafka.producer.ProducerSms;
import uzumtech.notification.jnotificationservice.mapper.NotificationMapper;
import uzumtech.notification.jnotificationservice.entity.MerchantEntity;
import uzumtech.notification.jnotificationservice.entity.NotificationEntity;
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
    ProducerEmail producerEmail;
    ProducerSms  producerSms;

    @Override
    public NotificationResponse sendEmail(NotificationEmailRequest notificationEmailRequest, Long merchantId) {

        MerchantEntity merchantEntity = merchantRepository
                .findById(merchantId).orElseThrow(() -> new UsernameNotFoundException("Merchant not found " + merchantId));

        NotificationEntity emailNotification = notificationMapper
                .toEmailNotification(notificationEmailRequest, merchantEntity);

        notificationRepository.save(emailNotification);

        producerEmail.send(NotificationEvent.builder()
                .notificationId(emailNotification.getId())
                .content(emailNotification.getContent())
                .recipient(emailNotification.getReceiver())
                .notificationType(NotificationType.EMAIL)
                .build());

        log.info("EMAIL notification sent | merchantId={} | phoneNumber={} | message='{}' | notificationId={}",
                merchantId,
                notificationEmailRequest.getEmail(),
                notificationEmailRequest.getContent(),
                emailNotification.getId());

        return notificationMapper.toResponse(emailNotification);
    }

    @Override
    public NotificationResponse sendSms(NotificationSmsRequest smsRequest, Long merchantId) {
        MerchantEntity merchantEntity = merchantRepository
                .findById(merchantId).orElseThrow(() -> new UsernameNotFoundException("Merchant not found " + merchantId));

        NotificationEntity smsNotification = notificationMapper
                .toSmsNotification(smsRequest, merchantEntity);

        notificationRepository.save(smsNotification);

        producerSms.send(NotificationEvent.builder()
                .notificationId(smsNotification.getId())
                .content(smsNotification.getContent())
                .recipient(smsNotification.getReceiver())
                .notificationType(NotificationType.SMS)
                .build());


        log.info("SMS notification sent | merchantId={} | phoneNumber={} | message='{}' | notificationId={}",
                merchantId,
                smsRequest.getReceiver(),
                smsRequest.getContent(),
                smsNotification.getId());

        return notificationMapper.toResponse(smsNotification);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatus(Long notificationId, Status status) {
        notificationRepository.updateStatusById(notificationId, status);
    }

    @Override
    public NotificationEntity getNotificationById(Long notificationId) {
        return notificationRepository.findByIdWithMerchant(notificationId)
                .orElseThrow(() -> new DataNotFoundException("User with id: " + notificationId + " not found"));
    }
}
