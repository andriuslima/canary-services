package xyz.andriuslima.canaryapi.domain.events;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateRepostEvent implements DomainEvent {
    private final String id;
    private final Integer author;
    private final Integer parent;
    private String type = "CreateRepost";
}
