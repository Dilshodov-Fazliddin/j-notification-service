package uzumtech.notification.jnotificationservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import uzumtech.notification.jnotificationservice.dto.request.NotificationEmailRequest;
import uzumtech.notification.jnotificationservice.dto.request.NotificationSmsRequest;
import uzumtech.notification.jnotificationservice.dto.response.NotificationResponse;
import uzumtech.notification.jnotificationservice.service.MerchantService;
import uzumtech.notification.jnotificationservice.service.NotificationService;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks( this );
    }



    @Test
    void shouldSendSmsNotification() {
        Long merchantId = 1L;
        NotificationSmsRequest request = new NotificationSmsRequest();
        NotificationResponse expectedResponse = new NotificationResponse();
        when( notificationService.sendSms( request, merchantId ) ).thenReturn( expectedResponse );

        ResponseEntity<NotificationResponse> response = notificationController.sendSms( merchantId, request );

        assertEquals( expectedResponse, response.getBody() );
        assertEquals( HttpStatus.OK, response.getStatusCode() );
        verify( notificationService, times( 1 ) ).sendSms( request, merchantId );
    }

    @Test
    void shouldSendEmailNotification() {
        Long merchantId = 1L;
        NotificationEmailRequest request = new NotificationEmailRequest();
        NotificationResponse expectedResponse = new NotificationResponse();
        when( notificationService.sendEmail( request, merchantId ) ).thenReturn( expectedResponse );

        ResponseEntity<NotificationResponse> response = notificationController.sendEmail( merchantId, request );

        assertEquals( expectedResponse, response.getBody() );
        assertEquals( HttpStatus.OK, response.getStatusCode() );
        verify( notificationService, times( 1 ) ).sendEmail( request, merchantId );
    }
}
