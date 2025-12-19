package uzumtech.notification.jnotificationservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uzumtech.notification.jnotificationservice.dto.request.MerchantRequest;
import uzumtech.notification.jnotificationservice.dto.response.MerchantResponse;
import uzumtech.notification.jnotificationservice.mapper.MerchantMapper;
import uzumtech.notification.jnotificationservice.entity.MerchantEntity;
import uzumtech.notification.jnotificationservice.repository.MerchantRepository;
import uzumtech.notification.jnotificationservice.service.impl.MerchantServiceImpl;
import uzumtech.notification.jnotificationservice.utils.PasswordGenerator;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MerchantServiceImplTest {

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    private MerchantMapper merchantMapper;

    @Mock
    private PasswordGenerator passwordGenerator;

    @InjectMocks
    private MerchantServiceImpl merchantService;

    private MerchantRequest testRequest;
    private MerchantEntity testEntity;
    private MerchantResponse testResponse;
    private final String password = "12321414";

    @BeforeEach
    void setUp() {
        testRequest = MerchantRequest.builder()
            .companyName("Test Company")
            .taxNumber("123456789")
            .login("testlogin")
            .webhook("https://webhook.example.com")
            .build();

        testEntity = MerchantEntity.builder()
            .id(1L)
            .companyName("Test Company")
            .taxNumber("123456789")
            .login("testlogin")
            .webhook("https://webhook.example.com")
            .password("encoded_password")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        testResponse = MerchantResponse.builder()
            .id(1L)
            .companyName("Test Company")
            .taxNumber("123456789")
            .login("testlogin")
            .webhook("https://webhook.example.com")
            .build();

    }

    @Test
    void create_Success() {
        String encodedPassword = "encoded_password_from_generator";

        when(merchantRepository.existsByLogin(testRequest.getLogin())).thenReturn(false);
        when(merchantRepository.existsByTaxNumber(testRequest.getTaxNumber())).thenReturn(false);
        when(passwordGenerator.generatePassword(password)).thenReturn(encodedPassword);
        doReturn(testEntity).when(merchantMapper).toEntity(any(MerchantRequest.class), anyString());
        when(merchantRepository.save(any(MerchantEntity.class))).thenReturn(testEntity);
        when(merchantMapper.toResponse(testEntity)).thenReturn(testResponse);

        MerchantResponse result = merchantService.create(testRequest);

        assertNotNull(result);
        assertEquals(testResponse.getId(), result.getId());
        assertEquals(testResponse.getCompanyName(), result.getCompanyName());
        assertEquals(testResponse.getTaxNumber(), result.getTaxNumber());
        assertEquals(testResponse.getLogin(), result.getLogin());
        assertEquals(testResponse.getWebhook(), result.getWebhook());

        verify(merchantRepository, times(1)).existsByLogin(testRequest.getLogin());
        verify(merchantRepository, times(1)).existsByTaxNumber(testRequest.getTaxNumber());
        verify(passwordGenerator, times(1)).generatePassword(password);
        verify(merchantMapper, times(1)).toEntity(any(MerchantRequest.class), anyString());
        verify(merchantRepository, times(1)).save(any(MerchantEntity.class));
        verify(merchantMapper, times(1)).toResponse(testEntity);
    }

    @Test
    void create_DuplicateLogin_ThrowsException() {
        when(merchantRepository.existsByLogin(testRequest.getLogin())).thenReturn(true);

        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> merchantService.create(testRequest)
        );

        assertEquals("Login already exists", exception.getMessage());
        verify(merchantRepository, times(1)).existsByLogin(testRequest.getLogin());
        verify(merchantRepository, never()).existsByTaxNumber(anyString());
        verify(passwordGenerator, never()).generatePassword(password);
        verify(merchantMapper, never()).toEntity(any(), anyString());
        verify(merchantRepository, never()).save(any());
    }

    @Test
    void create_DuplicateTaxNumber_ThrowsException() {
        when(merchantRepository.existsByLogin(testRequest.getLogin())).thenReturn(false);
        when(merchantRepository.existsByTaxNumber(testRequest.getTaxNumber())).thenReturn(true);

        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> merchantService.create(testRequest)
        );

        assertEquals("Tax number already exists", exception.getMessage());
        verify(merchantRepository, times(1)).existsByLogin(testRequest.getLogin());
        verify(merchantRepository, times(1)).existsByTaxNumber(testRequest.getTaxNumber());
        verify(passwordGenerator, never()).generatePassword(password);
        verify(merchantMapper, never()).toEntity(any(), anyString());
        verify(merchantRepository, never()).save(any());
    }

    @Test
    void create_PasswordGenerationWorks() {
        String encodedPassword = "super_secure_encoded_password_from_generator";

        when(merchantRepository.existsByLogin(testRequest.getLogin())).thenReturn(false);
        when(merchantRepository.existsByTaxNumber(testRequest.getTaxNumber())).thenReturn(false);
        when(passwordGenerator.generatePassword(password)).thenReturn(encodedPassword);
        doReturn(testEntity).when(merchantMapper).toEntity(any(MerchantRequest.class), anyString());
        when(merchantRepository.save(any(MerchantEntity.class))).thenReturn(testEntity);
        when(merchantMapper.toResponse(testEntity)).thenReturn(testResponse);

        merchantService.create(testRequest);

        verify(passwordGenerator).generatePassword(password);

        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(merchantMapper).toEntity(any(MerchantRequest.class), passwordCaptor.capture());

        String capturedPassword = passwordCaptor.getValue();
        assertNotNull(capturedPassword);
        assertEquals(encodedPassword, capturedPassword);

        ArgumentCaptor<MerchantEntity> entityCaptor = ArgumentCaptor.forClass(MerchantEntity.class);
        verify(merchantRepository).save(entityCaptor.capture());

        MerchantEntity savedEntity = entityCaptor.getValue();
        assertNotNull(savedEntity.getPassword());
    }

    @Test
    void create_VerifyAllDependencyCalls() {
        String encodedPassword = "encoded_password";

        when(merchantRepository.existsByLogin(testRequest.getLogin())).thenReturn(false);
        when(merchantRepository.existsByTaxNumber(testRequest.getTaxNumber())).thenReturn(false);
        when(passwordGenerator.generatePassword(password)).thenReturn(encodedPassword);
        doReturn(testEntity).when(merchantMapper).toEntity(any(MerchantRequest.class), anyString());
        when(merchantRepository.save(any(MerchantEntity.class))).thenReturn(testEntity);
        when(merchantMapper.toResponse(testEntity)).thenReturn(testResponse);

        MerchantResponse result = merchantService.create(testRequest);

        assertNotNull(result);

        verify(merchantRepository).existsByLogin(testRequest.getLogin());
        verify(merchantRepository).existsByTaxNumber(testRequest.getTaxNumber());
        verify(passwordGenerator).generatePassword(password);
        verify(merchantMapper).toEntity(any(MerchantRequest.class), anyString());
        verify(merchantRepository).save(testEntity);
        verify(merchantMapper).toResponse(testEntity);

        verifyNoMoreInteractions(merchantRepository, merchantMapper, passwordGenerator);
    }
}