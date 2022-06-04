package xyz.andriuslima.canaryapi.api.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xyz.andriuslima.canaryapi.domain.events.DomainEvent;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("!it")
public class KafkaNotifier implements EventNotifier {

    @Value("${canary.domain.event.topic}")
    private String topic;

    private final KafkaTemplate<String, DomainEvent> template;

    public void publish(DomainEvent event) {
        log.info("Sending event for entity {}", event.getId());
        template.send(topic, event.getType(), event);
    }

}