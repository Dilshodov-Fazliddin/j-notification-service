package uzumtech.notification.jnotificationservice.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uzumtech.notification.jnotificationservice.repository.MerchantRepository;

@Service
@RequiredArgsConstructor
public class MerchantDetailsService implements UserDetailsService {
    private final MerchantRepository merchantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return merchantRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
