package xyz.andriuslima.canaryapi.api.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;

@Configuration
public class KafkaConfiguration {

    @Value("${canary.domain.event.topic}")
    private String topic;

    @Value("${canary.domain.event.dlt}")
    private String dlt;

    @Bean
    public RecordMessageConverter converter() {
        return new JsonMessageConverter();
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic(topic, 1, (short) 1);
    }

    @Bean
    public NewTopic dlt() {
        return new NewTopic(dlt, 1, (short) 1);
    }
}