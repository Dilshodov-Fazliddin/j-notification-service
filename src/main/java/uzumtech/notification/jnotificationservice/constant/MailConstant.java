package uzumtech.notification.jnotificationservice.constant;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MailConstant {

    private final String email;

    public MailConstant(@Value("${spring.mail.username}") String email) {
        this.email = email;
    }

}
