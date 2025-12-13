package uzumtech.notification.jnotificationservice.repository;

import org.junit.jupiter.api.Test;
import uzumtech.notification.jnotificationservice.entity.NotificationEntity;

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

        when(repo.findById(1)).thenReturn( Optional.of( notification ) );

        Optional<NotificationEntity> result = repo.findById( 1 );

        assertTrue( result.isPresent() );
        assertEquals( notification, result.get() );
        verify( repo, times( 1 ) ).findById( 1 );
    }

    @Test
    void testDeleteById() {
        NotificationRepository repo = mock( NotificationRepository.class );

        repo.deleteById( 1 );

        verify( repo, times( 1 ) ).deleteById( 1 );
    }
}
