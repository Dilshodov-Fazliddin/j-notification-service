package uzumtech.notification.jnotificationservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.dto.request.MerchantRequest;
import uzumtech.notification.jnotificationservice.dto.response.MerchantResponse;
import uzumtech.notification.jnotificationservice.mapper.MerchantMapper;
import uzumtech.notification.jnotificationservice.model.MerchantEntity;
import uzumtech.notification.jnotificationservice.repository.MerchantRepository;
import uzumtech.notification.jnotificationservice.service.MerchantService;
import uzumtech.notification.jnotificationservice.utils.PasswordGenerator;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MerchantServiceImpl implements MerchantService {

    MerchantRepository merchantRepository;
    MerchantMapper merchantMapper;
    PasswordGenerator passwordGenerator;

    @Override
    public MerchantResponse create(MerchantRequest request) {
        validateRequest(request);

        String password = passwordGenerator.generatePassword();

        MerchantEntity merchant = merchantMapper.toEntity(request,password);
        merchant = merchantRepository.save(merchant);

        log.info("Merchant created | merchantId={} | companyName={}",
                merchant.getId(),
                merchant.getCompanyName());

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
