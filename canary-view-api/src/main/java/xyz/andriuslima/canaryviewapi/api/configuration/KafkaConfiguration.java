package xyz.andriuslima.canaryviewapi.api.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.*;

@Configuration
public class KafkaConfiguration {

    @Value("${canary.domain.event.topic.post-created}")
    private String postCreatedTopic;

    @Value("${canary.domain.event.topic.repost-created}")
    private String repostCreatedTopic;

    @Value("${canary.domain.event.topic.quote-repost-created}")
    private String quoteRepostCreatedTopic;

    @Bean
    public MessageConverter messageConverter() {
        return new BytesJsonMessageConverter();
    }

    @Bean
    public NewTopic createPostCreatedTopic() {
        return new NewTopic(postCreatedTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic createRepostCreatedTopic() {
        return new NewTopic(repostCreatedTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic createQuoteRepostCreatedTopic() {
        return new NewTopic(quoteRepostCreatedTopic, 1, (short) 1);
    }
}
