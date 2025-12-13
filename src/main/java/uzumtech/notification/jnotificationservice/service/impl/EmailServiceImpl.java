package uzumtech.notification.jnotificationservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uzumtech.notification.jnotificationservice.constant.MailConstant;
import uzumtech.notification.jnotificationservice.dto.event.NotificationEvent;
import uzumtech.notification.jnotificationservice.service.EmailService;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailServiceImpl implements EmailService {

    JavaMailSender mailSender;
    MailConstant mailConstant;

    @Override
    public void sendEmail(NotificationEvent notificationEvent) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(mailConstant.getEmail());
            mailMessage.setTo(notificationEvent.recipient());
            mailMessage.setText(notificationEvent.content());
            mailSender.send(mailMessage);
            log.info("Email sent successfully to {}", notificationEvent.recipient());
        }catch (Exception e){
            log.error("Failed to send status notification to {}: {}", notificationEvent.recipient(), e.getMessage());
        }
    }
}
