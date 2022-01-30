package xyz.andriuslima.canaryapi.application.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.andriuslima.canaryapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryapi.api.integration.EventNotifier;
import xyz.andriuslima.canaryapi.application.commands.CreatePostCommand;
import xyz.andriuslima.canaryapi.application.response.CreatePostResponse;
import xyz.andriuslima.canaryapi.application.validation.CreatePostValidation;
import xyz.andriuslima.canaryapi.domain.events.CreatePostEvent;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatePostHandler {

    private final CreatePostValidation validation;
    private final EventNotifier notifier;

    public CreatePostResponse handle(UserInfo user, CreatePostCommand command) {
        validation.validate(command);

        var id = UUID.randomUUID().toString();
        var createPostEvent = new CreatePostEvent(id, user.getUserId(), command.getContent());

        notifier.publish(createPostEvent);

        return new CreatePostResponse(createPostEvent.getContent());
    }
}
