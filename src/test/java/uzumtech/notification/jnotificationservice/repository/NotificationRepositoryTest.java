package uzumtech.notification.jnotificationservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uzumtech.notification.jnotificationservice.entity.NotificationEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationRepositoryTest {

    private NotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        notificationRepository = mock(NotificationRepository.class);
    }

    @Test
    void shouldSaveNotification() {
        NotificationEntity notification = new NotificationEntity();
        notification.setContent("Hello");

        when(notificationRepository.save(notification)).thenReturn(notification);

        NotificationEntity saved = notificationRepository.save(notification);

        assertNotNull(saved);
        assertEquals("Hello", saved.getContent());
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void shouldFindNotificationById() {
        NotificationEntity notification = new NotificationEntity();
        notification.setContent("Find me");

        when(notificationRepository.findById(1l)).thenReturn(Optional.of(notification));

        Optional<NotificationEntity> result = notificationRepository.findById(1l);

        assertTrue(result.isPresent());
        assertEquals("Find me", result.get().getContent());
        verify(notificationRepository, times(1)).findById(1l);
    }

    @Test
    void shouldFindAllNotifications() {
        NotificationEntity n1 = new NotificationEntity();
        NotificationEntity n2 = new NotificationEntity();

        when(notificationRepository.findAll()).thenReturn(List.of(n1, n2));

        List<NotificationEntity> all = notificationRepository.findAll();

        assertEquals(2, all.size());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void shouldDeleteNotificationById() {
        doNothing().when(notificationRepository).deleteById(1l);

        notificationRepository.deleteById(1l);

        verify(notificationRepository, times(1)).deleteById(1l);
    }
}
