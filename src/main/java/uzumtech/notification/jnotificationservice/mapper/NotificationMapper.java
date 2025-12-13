package uzumtech.notification.jnotificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uzumtech.notification.jnotificationservice.dto.request.NotificationEmailRequest;
import uzumtech.notification.jnotificationservice.dto.request.NotificationSmsRequest;
import uzumtech.notification.jnotificationservice.dto.response.NotificationResponse;
import uzumtech.notification.jnotificationservice.entity.MerchantEntity;
import uzumtech.notification.jnotificationservice.entity.NotificationEntity;
import uzumtech.notification.jnotificationservice.constant.enums.NotificationType;
import uzumtech.notification.jnotificationservice.constant.enums.Status;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {NotificationType.class, Status.class}
)
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "merchant", source = "merchant")
    @Mapping(target = "status", expression = "java(Status.CREATED)")
    @Mapping(target = "type", expression = "java(NotificationType.SMS)")
    @Mapping(target = "receiver", source = "request.receiver")
    @Mapping(target = "content", source = "request.content")
    NotificationEntity toSmsNotification(NotificationSmsRequest request, MerchantEntity merchant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "merchant", source = "merchant")
    @Mapping(target = "status", expression = "java(Status.CREATED)")
    @Mapping(target = "type", expression = "java(NotificationType.EMAIL)")
    @Mapping(target = "receiver", source = "request.email")
    @Mapping(target = "content", source = "request.content")
    NotificationEntity toEmailNotification(NotificationEmailRequest request, MerchantEntity merchant);

    @Mapping(target = "merchantId", source = "merchant.id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "receiver", source = "receiver")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    NotificationResponse toResponse(NotificationEntity entity);
}
