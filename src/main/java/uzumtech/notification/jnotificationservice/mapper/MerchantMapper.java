package uzumtech.notification.jnotificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uzumtech.notification.jnotificationservice.dto.request.MerchantRequest;
import uzumtech.notification.jnotificationservice.dto.response.MerchantResponse;
import uzumtech.notification.jnotificationservice.entity.MerchantEntity;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MerchantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "companyName", source = "request.companyName")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    MerchantEntity toEntity(MerchantRequest request,String password);

    MerchantResponse toResponse(MerchantEntity entity);

}
