package uzumtech.notification.jnotificationservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic smsTopic() {
        return TopicBuilder.name("sms-notifications").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic emailTopic() {
        return TopicBuilder.name("email-notifications").partitions(3).replicas(1).build();
    }
}
// docker exec -it j-notification-service-kafka-1 kafka-topics --bootstrap-server localhost:9092 --list