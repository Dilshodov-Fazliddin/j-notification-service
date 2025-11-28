package uzumtech.notification.jnotificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uzumtech.notification.jnotificationservice.dto.request.MerchantRequest;
import uzumtech.notification.jnotificationservice.dto.response.MerchantResponse;
import uzumtech.notification.jnotificationservice.model.MerchantEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MerchantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "companyName", source = "companyName")
    @Mapping(target = "password", ignore = true)
    MerchantEntity toEntity(MerchantRequest request);

    MerchantResponse toResponse(MerchantEntity entity);

}
