package xyz.andriuslima.canaryapi.api.integration;


import xyz.andriuslima.canaryapi.domain.events.DomainEvent;

public interface EventNotifier {
    void publish(DomainEvent event);
}
