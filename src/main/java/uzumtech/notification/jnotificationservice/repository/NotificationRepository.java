package uzumtech.notification.jnotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uzumtech.notification.jnotificationservice.model.NotificationEntity;
import uzumtech.notification.jnotificationservice.model.enums.Status;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {

    @Modifying
    @Query("update NotificationEntity n set n.status = :status where n.id = :notificationId ")
    void updateStatusById(@Param("notificationId") Long notificationId, @Param("status") Status status);
}
