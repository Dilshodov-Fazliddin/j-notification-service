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
import uzumtech.notification.jnotificationservice.entity.MerchantEntity;
import uzumtech.notification.jnotificationservice.mapper.MerchantMapper;
import uzumtech.notification.jnotificationservice.repository.MerchantRepository;
import uzumtech.notification.jnotificationservice.service.impl.MerchantServiceImpl;
import uzumtech.notification.jnotificationservice.utils.PasswordGenerator;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MerchantServiceImplTest {

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
    void create_success() {
        when(merchantRepository.existsByLogin(anyString())).thenReturn(false);
        when(merchantRepository.existsByTaxNumber(anyString())).thenReturn(false);

        when(passwordGenerator.generatePassword(any()))
                .thenReturn("encoded_password");

        when(merchantMapper.toEntity(any(), any()))
                .thenReturn(testEntity);

        when(merchantRepository.save(any()))
                .thenReturn(testEntity);

        when(merchantMapper.toResponse(testEntity))
                .thenReturn(testResponse);

        MerchantResponse result = merchantService.create(testRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(passwordGenerator).generatePassword(any());
        verify(merchantMapper).toEntity(any(), any());
        verify(merchantRepository).save(any());
    }

    @Test
    void create_duplicateLogin() {
        when(merchantRepository.existsByLogin(anyString()))
                .thenReturn(true);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> merchantService.create(testRequest)
        );

        assertEquals("Login already exists", ex.getMessage());

        verify(passwordGenerator, never()).generatePassword(any());
        verify(merchantMapper, never()).toEntity(any(), any());
    }

    @Test
    void create_duplicateTaxNumber() {
        when(merchantRepository.existsByLogin(anyString()))
                .thenReturn(false);

        when(merchantRepository.existsByTaxNumber(anyString()))
                .thenReturn(true);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> merchantService.create(testRequest)
        );

        assertEquals("Tax number already exists", ex.getMessage());

        verify(passwordGenerator, never()).generatePassword(any());
        verify(merchantMapper, never()).toEntity(any(), any());
    }

    @Test
    void create_passwordPassedToMapper() {
        when(merchantRepository.existsByLogin(anyString())).thenReturn(false);
        when(merchantRepository.existsByTaxNumber(anyString())).thenReturn(false);

        when(passwordGenerator.generatePassword(any()))
                .thenReturn("encoded_password");

        when(merchantMapper.toEntity(any(), any()))
                .thenReturn(testEntity);

        when(merchantRepository.save(any()))
                .thenReturn(testEntity);

        when(merchantMapper.toResponse(any()))
                .thenReturn(testResponse);

        merchantService.create(testRequest);

        ArgumentCaptor<String> passwordCaptor =
                ArgumentCaptor.forClass(String.class);

        verify(merchantMapper)
                .toEntity(any(), passwordCaptor.capture());

        assertEquals("encoded_password", passwordCaptor.getValue());
    }
}
