package uzumtech.notification.jnotificationservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uzumtech.notification.jnotificationservice.constant.enums.NotificationType;
import uzumtech.notification.jnotificationservice.constant.enums.Status;
import uzumtech.notification.jnotificationservice.dto.request.NotificationEmailRequest;
import uzumtech.notification.jnotificationservice.dto.request.NotificationSmsRequest;
import uzumtech.notification.jnotificationservice.dto.response.NotificationResponse;
import uzumtech.notification.jnotificationservice.entity.MerchantEntity;
import uzumtech.notification.jnotificationservice.entity.NotificationEntity;
import uzumtech.notification.jnotificationservice.kafka.producer.ProducerEmail;
import uzumtech.notification.jnotificationservice.kafka.producer.ProducerSms;
import uzumtech.notification.jnotificationservice.mapper.NotificationMapper;
import uzumtech.notification.jnotificationservice.repository.MerchantRepository;
import uzumtech.notification.jnotificationservice.repository.NotificationRepository;
import uzumtech.notification.jnotificationservice.service.impl.NotificationServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private ProducerEmail producerEmail;

    @Mock
    private ProducerSms producerSms;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private MerchantEntity merchant;
    private NotificationEntity notification;
    private NotificationResponse response;

    @BeforeEach
    void setUp() {
        merchant = MerchantEntity.builder()
                .id(1L)
                .companyName("Test Company")
                .login("testlogin")
                .password("encoded_password")
                .taxNumber("123456789")
                .webhook("https://webhook.example.com")
                .createdAt(LocalDateTime.now())
                .build();

        notification = NotificationEntity.builder()
                .id(1L)
                .type(NotificationType.EMAIL)
                .status(Status.CREATED)
                .content("Test content")
                .receiver("test@example.com")
                .merchant(merchant)
                .createdAt(LocalDateTime.now())
                .build();

        response = NotificationResponse.builder()
                .id(1L)
                .merchantId(1L)
                .content("Test content")
                .receiver("test@example.com")
                .type("EMAIL")
                .status("CREATED")
                .build();
    }

    @Test
    void sendEmail_success() {
        Long merchantId = 1L;

        NotificationEmailRequest request = NotificationEmailRequest.builder()
                .email("test@example.com")
                .content("Email content")
                .build();

        when(merchantRepository.findById(merchantId))
                .thenReturn(Optional.of(merchant));

        when(notificationMapper.toEmailNotification(any(), any()))
                .thenReturn(notification);

        when(notificationRepository.save(any()))
                .thenReturn(notification);

        when(notificationMapper.toResponse(notification))
                .thenReturn(response);

        doNothing().when(producerEmail).send(any());

        NotificationResponse result =
                notificationService.sendEmail(request, merchantId);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(merchantRepository).findById(merchantId);
        verify(notificationMapper).toEmailNotification(any(), any());
        verify(notificationRepository).save(any());
        verify(producerEmail).send(any());
        verify(notificationMapper).toResponse(notification);
    }

    @Test
    void sendEmail_merchantNotFound() {
        Long merchantId = 999L;

        when(merchantRepository.findById(merchantId))
                .thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> notificationService.sendEmail(
                        NotificationEmailRequest.builder().build(),
                        merchantId
                )
        );

        assertTrue(ex.getMessage().contains("Merchant not found"));

        verify(notificationMapper, never()).toEmailNotification(any(), any());
        verify(notificationRepository, never()).save(any());
        verify(producerEmail, never()).send(any());
    }

    @Test
    void sendSms_success() {
        Long merchantId = 1L;

        NotificationSmsRequest request = NotificationSmsRequest.builder()
                .receiver("998901234567")
                .content("SMS text")
                .build();

        NotificationEntity smsNotification = NotificationEntity.builder()
                .id(2L)
                .type(NotificationType.SMS)
                .status(Status.CREATED)
                .content("SMS text")
                .receiver("998901234567")
                .merchant(merchant)
                .build();

        NotificationResponse smsResponse = NotificationResponse.builder()
                .id(2L)
                .merchantId(1L)
                .content("SMS text")
                .receiver("998901234567")
                .type("SMS")
                .status("CREATED")
                .build();

        when(merchantRepository.findById(merchantId))
                .thenReturn(Optional.of(merchant));

        when(notificationMapper.toSmsNotification(any(), any()))
                .thenReturn(smsNotification);

        when(notificationRepository.save(any()))
                .thenReturn(smsNotification);

        when(notificationMapper.toResponse(smsNotification))
                .thenReturn(smsResponse);

        doNothing().when(producerSms).send(any());

        NotificationResponse result =
                notificationService.sendSms(request, merchantId);

        assertNotNull(result);
        assertEquals("SMS", result.getType());

        verify(notificationMapper).toSmsNotification(any(), any());
        verify(notificationRepository).save(any());
        verify(producerSms).send(any());
        verify(notificationMapper).toResponse(smsNotification);
    }

    @Test
    void sendSms_merchantNotFound() {
        Long merchantId = 999L;

        when(merchantRepository.findById(merchantId))
                .thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> notificationService.sendSms(
                        NotificationSmsRequest.builder().build(),
                        merchantId
                )
        );

        assertTrue(ex.getMessage().contains("Merchant not found"));

        verify(notificationMapper, never()).toSmsNotification(any(), any());
        verify(notificationRepository, never()).save(any());
        verify(producerSms, never()).send(any());
    }
}
