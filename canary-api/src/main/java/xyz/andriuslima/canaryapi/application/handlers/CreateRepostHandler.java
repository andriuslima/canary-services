package xyz.andriuslima.canaryapi.application.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.andriuslima.canaryapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryapi.api.integration.EventNotifier;
import xyz.andriuslima.canaryapi.application.commands.CreateRepostCommand;
import xyz.andriuslima.canaryapi.application.response.CreateRepostResponse;
import xyz.andriuslima.canaryapi.domain.events.CreateRepostEvent;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CreateRepostHandler {
    private final EventNotifier notifier;

    public CreateRepostResponse handle(UserInfo user, CreateRepostCommand command) {
        var id = UUID.randomUUID().toString();
        var createRepostEvent = new CreateRepostEvent(id, user.getUserId(), command.getParent());

        notifier.publish(createRepostEvent);

        return new CreateRepostResponse(createRepostEvent.getParent());
    }
}
