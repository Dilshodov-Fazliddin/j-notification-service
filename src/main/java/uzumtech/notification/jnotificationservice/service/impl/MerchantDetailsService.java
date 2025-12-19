package uzumtech.notification.jnotificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.entity.MerchantEntity;
import uzumtech.notification.jnotificationservice.repository.MerchantRepository;

@Service
@RequiredArgsConstructor
public class MerchantDetailsService implements UserDetailsService {

    private final MerchantRepository merchantRepository;

    @Override
    public UserDetails loadUserByUsername(String client) {

        MerchantEntity merchant = merchantRepository
                .findByLogin(client)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Merchant not found"));

        return User.builder()
                .username(merchant.getLogin())
                .password(merchant.getPassword())
                .roles("MERCHANT")
                .build();
    }
}
