package uzumtech.notification.jnotificationservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.dto.request.MerchantRequest;
import uzumtech.notification.jnotificationservice.dto.response.MerchantResponse;
import uzumtech.notification.jnotificationservice.mapper.MerchantMapper;
import uzumtech.notification.jnotificationservice.model.MerchantEntity;
import uzumtech.notification.jnotificationservice.repository.MerchantRepository;
import uzumtech.notification.jnotificationservice.service.MerchantService;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MerchantServiceImpl implements MerchantService {

    MerchantRepository merchantRepository;
    MerchantMapper merchantMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public MerchantResponse create(MerchantRequest request) {
        validateRequest(request);

        MerchantEntity merchant = merchantMapper.toEntity(request);
        merchant.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        merchant = merchantRepository.save(merchant);

        return merchantMapper.toResponse(merchant);
    }

    private void validateRequest(MerchantRequest request) {
        if (merchantRepository.existsByLogin(request.getLogin())) {
            throw new RuntimeException("Login already exists");
        }

        if (merchantRepository.existsByTaxNumber(request.getTaxNumber())) {
            throw new RuntimeException("Tax number already exists");
        }
    }
}
