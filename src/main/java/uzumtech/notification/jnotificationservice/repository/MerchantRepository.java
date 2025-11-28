package uzumtech.notification.jnotificationservice.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import uzumtech.notification.jnotificationservice.model.MerchantEntity;

import java.util.Optional;


public interface MerchantRepository extends JpaRepository<MerchantEntity, Long> {

    Optional<MerchantEntity> findByUsername(String username);

    boolean existsByLogin(@NotBlank(message = "login is blank") String login);

    boolean existsByTaxNumber(@NotBlank(message = "tax number is blank") String taxNumber);
}
