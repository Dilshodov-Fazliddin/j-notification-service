package uzumtech.notification.jnotificationservice.repository;

import org.junit.jupiter.api.Test;
import uzumtech.notification.jnotificationservice.entity.MerchantEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class MerchantsRepositoryTest {

    private final MerchantRepository merchantRepository = mock( MerchantRepository.class );

    @Test
    void shouldRegisterMerchant() {
        MerchantEntity merchant = mock( MerchantEntity.class );

        when( merchantRepository.save( any() ) ).thenReturn( merchant );

        MerchantEntity saved = merchantRepository.save( merchant );

        assertNotNull( saved );
        verify( merchantRepository ).save( merchant );
    }

    @Test
    void shouldFindByUsername() {
        MerchantEntity merchant = mock( MerchantEntity.class );

        when( merchantRepository.findByUsername( "user1" ) )
                .thenReturn( Optional.of( merchant ) );

        Optional<MerchantEntity> result = merchantRepository.findByUsername( "user1" );

        assertTrue( result.isPresent() );
        verify( merchantRepository ).findByUsername( "user1" );
    }

    @Test
    void shouldCheckExistsByLogin() {
        when( merchantRepository.existsByLogin( "login123" ) )
                .thenReturn( true );

        boolean exists = merchantRepository.existsByLogin( "login123" );

        assertTrue( exists );
        verify( merchantRepository ).existsByLogin( "login123" );
    }
}
