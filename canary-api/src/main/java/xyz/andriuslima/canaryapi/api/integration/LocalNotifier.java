package xyz.andriuslima.canaryapi.api.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import xyz.andriuslima.canaryapi.domain.events.DomainEvent;

@Slf4j
@Service
@Profile("it")
public class LocalNotifier implements EventNotifier {

    public void publish(DomainEvent event) {
        log.info("Locally Sending event for entity {} = {}", event.getId(), event);
    }

}
