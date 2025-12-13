package uzumtech.notification.jnotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uzumtech.notification.jnotificationservice.entity.MerchantEntity;

import java.util.Optional;


public interface MerchantRepository extends JpaRepository<MerchantEntity, Long> {

    Optional<MerchantEntity> findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByTaxNumber(String taxNumber);
}
