package uzumtech.notification.jnotificationservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uzumtech.notification.jnotificationservice.dto.request.MerchantRequest;
import uzumtech.notification.jnotificationservice.dto.response.MerchantResponse;
import uzumtech.notification.jnotificationservice.service.MerchantService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MerchantControllerTest {
    @Mock
    private MerchantService merchantService;
    @InjectMocks
    private MerchantController merchantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks( this );
    }

    @Test
    void shouldRegisterMerchant() {
        MerchantRequest request = new MerchantRequest();
        MerchantResponse expectedResponse = new MerchantResponse();
        when( merchantService.create( request ) ).thenReturn( expectedResponse );

        MerchantResponse response = merchantController.registration( request );

        assertEquals( expectedResponse, response );
        verify( merchantService, times( 1 ) ).create( request );
    }
}
