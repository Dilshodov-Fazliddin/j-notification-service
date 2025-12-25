package uzumtech.notification.jnotificationservice.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import uzumtech.notification.jnotificationservice.handler.RestClientExceptionHandler;

@Configuration
public class RestClientConfiguration {
    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .defaultStatusHandler(new RestClientExceptionHandler())
                .build();
    }
}
