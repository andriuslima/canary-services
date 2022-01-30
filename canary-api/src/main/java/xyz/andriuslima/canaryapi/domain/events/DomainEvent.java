package xyz.andriuslima.canaryapi.domain.events;

public interface DomainEvent {
    String getId();

    String getType();
}
