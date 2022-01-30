package xyz.andriuslima.canaryapi.application.handlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.andriuslima.canaryapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryapi.api.integration.EventNotifier;
import xyz.andriuslima.canaryapi.application.commands.CreatePostCommand;
import xyz.andriuslima.canaryapi.application.validation.CreatePostValidation;
import xyz.andriuslima.canaryapi.domain.events.CreatePostEvent;

@ExtendWith(MockitoExtension.class)
class CreatePostHandlerTest {

  @Mock
  CreatePostValidation validation;
  @Mock
  EventNotifier notifier;

  @InjectMocks
  CreatePostHandler handler;

  @Test
  void handleShouldCallProperServices() {
    var userRequest = new UserInfo(123);
    var command = new CreatePostCommand();
    command.setContent("content");

    var response = handler.handle(userRequest, command);

    verify(validation).validate(command);
    verify(notifier).publish(any(CreatePostEvent.class));
  }
}