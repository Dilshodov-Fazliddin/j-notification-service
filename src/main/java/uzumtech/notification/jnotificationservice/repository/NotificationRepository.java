package uzumtech.notification.jnotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uzumtech.notification.jnotificationservice.model.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

}
