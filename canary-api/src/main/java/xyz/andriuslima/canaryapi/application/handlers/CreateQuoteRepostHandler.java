package xyz.andriuslima.canaryapi.application.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.andriuslima.canaryapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryapi.api.integration.EventNotifier;
import xyz.andriuslima.canaryapi.application.commands.CreateQuoteRepostCommand;
import xyz.andriuslima.canaryapi.application.response.CreateQuoteRepostResponse;
import xyz.andriuslima.canaryapi.application.validation.CreateQuoteRepostValidation;
import xyz.andriuslima.canaryapi.domain.events.CreateQuoteRepostEvent;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CreateQuoteRepostHandler {

    private final CreateQuoteRepostValidation validation;
    private final EventNotifier notifier;

    public CreateQuoteRepostResponse handle(UserInfo user, CreateQuoteRepostCommand command) {
        validation.validate(command);

        var id = UUID.randomUUID().toString();
        var event = new CreateQuoteRepostEvent(id, user.getUserId(), command.getContent(), command.getParent());

        notifier.publish(event);

        return new CreateQuoteRepostResponse(event.getContent(), event.getParent());
    }
}
