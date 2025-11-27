package uzumtech.notification.jnotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uzumtech.notification.jnotificationservice.model.MerchantEntity;


public interface MerchantRepository extends JpaRepository<MerchantEntity, Long> {
}
