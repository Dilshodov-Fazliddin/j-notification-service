package uzumtech.notification.jnotificationservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uzumtech.notification.jnotificationservice.entity.MerchantEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MerchantsRepositoryTest {

    @Autowired
    private MerchantRepository merchantRepository;

    @Test
    void shouldRegisterMerchant() {
        MerchantEntity merchant = new MerchantEntity();
        merchant.setLogin("login123");

        MerchantEntity saved = merchantRepository.save(merchant);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void shouldFindByUsername() {
        MerchantEntity merchant = new MerchantEntity();
        merchant.setLogin("login123");
        merchantRepository.save(merchant);

        Optional<MerchantEntity> result =
                merchantRepository.findByLogin("login123");

        assertThat(result).isPresent();
    }

    @Test
    void shouldCheckExistsByLogin() {
        MerchantEntity merchant = new MerchantEntity();
        merchant.setLogin("login123");
        merchantRepository.save(merchant);

        boolean exists =
                merchantRepository.existsByLogin("login123");

        assertThat(exists).isTrue();
    }
}
