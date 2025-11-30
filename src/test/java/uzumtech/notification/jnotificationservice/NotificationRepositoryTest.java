package uzumtech.notification.jnotificationservice;

import org.junit.jupiter.api.Test;
import uzumtech.notification.jnotificationservice.model.NotificationEntity;
import uzumtech.notification.jnotificationservice.repository.NotificationRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationRepositoryTest {

    @Test
    void testSaveNotification() {
        NotificationRepository repo = mock( NotificationRepository.class );
        NotificationEntity notification = new NotificationEntity();

        when( repo.save( notification ) ).thenReturn( notification );

        NotificationEntity saved = repo.save( notification );

        assertNotNull( saved );
        verify( repo, times( 1 ) ).save( notification );
    }

    @Test
    void testFindById() {
        NotificationRepository repo = mock( NotificationRepository.class );
        NotificationEntity notification = new NotificationEntity();

        when( repo.findById( 1L ) ).thenReturn( Optional.of( notification ) );

        Optional<NotificationEntity> result = repo.findById( 1L );

        assertTrue( result.isPresent() );
        assertEquals( notification, result.get() );
        verify( repo, times( 1 ) ).findById( 1L );
    }

    @Test
    void testDeleteById() {
        NotificationRepository repo = mock( NotificationRepository.class );

        repo.deleteById( 1L );

        verify( repo, times( 1 ) ).deleteById( 1L );
    }
}
