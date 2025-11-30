package uzumtech.notification.jnotificationservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uzumtech.notification.jnotificationservice.dto.request.NotificationEmailRequest;
import uzumtech.notification.jnotificationservice.dto.request.NotificationSmsRequest;
import uzumtech.notification.jnotificationservice.dto.response.NotificationResponse;
import uzumtech.notification.jnotificationservice.mapper.NotificationMapper;
import uzumtech.notification.jnotificationservice.model.MerchantEntity;
import uzumtech.notification.jnotificationservice.model.NotificationEntity;
import uzumtech.notification.jnotificationservice.model.enums.NotificationType;
import uzumtech.notification.jnotificationservice.model.enums.Status;
import uzumtech.notification.jnotificationservice.repository.MerchantRepository;
import uzumtech.notification.jnotificationservice.repository.NotificationRepository;
import uzumtech.notification.jnotificationservice.service.impl.NotificationServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private MerchantEntity testMerchant;
    private NotificationEntity testNotification;
    private NotificationResponse testResponse;

    @BeforeEach
    void setUp() {
        testMerchant = MerchantEntity.builder()
                .id(1L)
                .companyName("Test Company")
                .login("testlogin")
                .password("encoded_password")
                .taxNumber("123456789")
                .webhook("https://webhook.example.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testNotification = NotificationEntity.builder()
                .id(1L)
                .type(NotificationType.EMAIL)
                .status(Status.CREATED)
                .content("Test content")
                .receiver("test@example.com")
                .merchant(testMerchant)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testResponse = NotificationResponse.builder()
                .id(1L)
                .merchantId(1L)
                .content("Test content")
                .receiver("test@example.com")
                .type("EMAIL")
                .status("CREATED")
                .createdAt("2025-01-01 12:00:00")
                .build();
    }

    @Test
    void sendEmail_Success() {
        Long merchantId = 1L;
        NotificationEmailRequest request = NotificationEmailRequest.builder()
                .email("test@example.com")
                .content("Test email content")
                .build();

        when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(testMerchant));
        when(notificationMapper.toEmailNotification(eq(request), eq(testMerchant))).thenReturn(testNotification);
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(testNotification);
        when(notificationMapper.toResponse(testNotification)).thenReturn(testResponse);

        NotificationResponse result = notificationService.sendEmail(request, merchantId);

        assertNotNull(result);
        assertEquals(testResponse.getId(), result.getId());
        assertEquals(testResponse.getMerchantId(), result.getMerchantId());
        assertEquals(testResponse.getContent(), result.getContent());
        assertEquals(testResponse.getReceiver(), result.getReceiver());
        verify(merchantRepository, times(1)).findById(merchantId);
        verify(notificationMapper, times(1)).toEmailNotification(request, testMerchant);
        verify(notificationRepository, times(1)).save(testNotification);
        verify(notificationMapper, times(1)).toResponse(testNotification);
    }

    @Test
    void sendEmail_MerchantNotFound_ThrowsException() {
        Long merchantId = 999L;
        NotificationEmailRequest request = NotificationEmailRequest.builder()
                .email("test@example.com")
                .content("Test email content")
                .build();

        when(merchantRepository.findById(merchantId)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> notificationService.sendEmail(request, merchantId)
        );

        assertTrue(exception.getMessage().contains("Merchant not found"));
        assertTrue(exception.getMessage().contains(String.valueOf(merchantId)));
        verify(merchantRepository, times(1)).findById(merchantId);
        verify(notificationMapper, never()).toEmailNotification(any(), any());
        verify(notificationRepository, never()).save(any());
    }

    @Test
    void sendEmail_VerifyMapperAndRepositoryCalls() {
        Long merchantId = 1L;
        NotificationEmailRequest request = NotificationEmailRequest.builder()
                .email("user@example.com")
                .content("Important notification")
                .build();

        NotificationEntity emailNotification = NotificationEntity.builder()
                .type(NotificationType.EMAIL)
                .status(Status.CREATED)
                .content(request.getContent())
                .receiver(request.getEmail())
                .merchant(testMerchant)
                .build();

        when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(testMerchant));
        when(notificationMapper.toEmailNotification(request, testMerchant)).thenReturn(emailNotification);
        when(notificationRepository.save(emailNotification)).thenReturn(emailNotification);
        when(notificationMapper.toResponse(emailNotification)).thenReturn(testResponse);

        notificationService.sendEmail(request, merchantId);

        verify(notificationMapper).toEmailNotification(request, testMerchant);
        verify(notificationRepository).save(emailNotification);
        verify(notificationMapper).toResponse(emailNotification);
    }

    @Test
    void sendSms_Success() {
        Long merchantId = 1L;
        NotificationSmsRequest request = NotificationSmsRequest.builder()
                .receiver("998901234567")
                .content("Test SMS content")
                .build();

        NotificationEntity smsNotification = NotificationEntity.builder()
                .id(2L)
                .type(NotificationType.SMS)
                .status(Status.CREATED)
                .content("Test SMS content")
                .receiver("998901234567")
                .merchant(testMerchant)
                .createdAt(LocalDateTime.now())
                .build();

        NotificationResponse smsResponse = NotificationResponse.builder()
                .id(2L)
                .merchantId(1L)
                .content("Test SMS content")
                .receiver("998901234567")
                .type("SMS")
                .status("CREATED")
                .createdAt("2025-01-01 12:00:00")
                .build();

        when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(testMerchant));
        when(notificationMapper.toSmsNotification(eq(request), eq(testMerchant))).thenReturn(smsNotification);
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(smsNotification);
        when(notificationMapper.toResponse(smsNotification)).thenReturn(smsResponse);

        NotificationResponse result = notificationService.sendSms(request, merchantId);

        assertNotNull(result);
        assertEquals(smsResponse.getId(), result.getId());
        assertEquals(smsResponse.getMerchantId(), result.getMerchantId());
        assertEquals(smsResponse.getContent(), result.getContent());
        assertEquals(smsResponse.getReceiver(), result.getReceiver());
        assertEquals("SMS", result.getType());
        verify(merchantRepository, times(1)).findById(merchantId);
        verify(notificationMapper, times(1)).toSmsNotification(request, testMerchant);
        verify(notificationRepository, times(1)).save(smsNotification);
        verify(notificationMapper, times(1)).toResponse(smsNotification);
    }

    @Test
    void sendSms_MerchantNotFound_ThrowsException() {
        Long merchantId = 999L;
        NotificationSmsRequest request = NotificationSmsRequest.builder()
                .receiver("998901234567")
                .content("Test SMS content")
                .build();

        when(merchantRepository.findById(merchantId)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> notificationService.sendSms(request, merchantId)
        );

        assertTrue(exception.getMessage().contains("Merchant not found"));
        assertTrue(exception.getMessage().contains(String.valueOf(merchantId)));
        verify(merchantRepository, times(1)).findById(merchantId);
        verify(notificationMapper, never()).toSmsNotification(any(), any());
        verify(notificationRepository, never()).save(any());
    }

    @Test
    void sendSms_VerifyMapperAndRepositoryCalls() {
        Long merchantId = 1L;
        NotificationSmsRequest request = NotificationSmsRequest.builder()
                .receiver("998901234567")
                .content("Urgent SMS")
                .build();

        NotificationEntity smsNotification = NotificationEntity.builder()
                .type(NotificationType.SMS)
                .status(Status.CREATED)
                .content(request.getContent())
                .receiver(request.getReceiver())
                .merchant(testMerchant)
                .build();

        when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(testMerchant));
        when(notificationMapper.toSmsNotification(request, testMerchant)).thenReturn(smsNotification);
        when(notificationRepository.save(smsNotification)).thenReturn(smsNotification);
        when(notificationMapper.toResponse(smsNotification)).thenReturn(testResponse);

        notificationService.sendSms(request, merchantId);

        verify(notificationMapper).toSmsNotification(request, testMerchant);
        verify(notificationRepository).save(smsNotification);
        verify(notificationMapper).toResponse(smsNotification);
    }
}
