package uzumtech.notification.jnotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uzumtech.notification.jnotificationservice.constant.enums.Status;
import uzumtech.notification.jnotificationservice.entity.NotificationEntity;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Modifying
    @Query("update NotificationEntity n set n.status = :status where n.id = :notificationId ")
    void updateStatusById(@Param("notificationId") Long notificationId, @Param("status") Status status);

    @Query("SELECT n FROM NotificationEntity n JOIN FETCH n.merchant WHERE n.id = :id")
    Optional<NotificationEntity> findByIdWithMerchant(@Param("id") Long id);

}
