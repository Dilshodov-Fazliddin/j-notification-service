package uzumtech.notification.jnotificationservice.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
@RequiredArgsConstructor
@Component
public class PasswordGenerator {

    PasswordEncoder passwordEncoder;

    public String generatePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
