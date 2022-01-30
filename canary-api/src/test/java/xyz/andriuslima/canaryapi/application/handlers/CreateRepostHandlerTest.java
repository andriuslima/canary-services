package xyz.andriuslima.canaryapi.application.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.andriuslima.canaryapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryapi.api.integration.EventNotifier;
import xyz.andriuslima.canaryapi.application.commands.CreateRepostCommand;
import xyz.andriuslima.canaryapi.domain.events.CreateRepostEvent;

@ExtendWith(MockitoExtension.class)
class CreateRepostHandlerTest {

  @Mock
  EventNotifier notifier;

  @InjectMocks
  CreateRepostHandler handler;

  @Test
  void handleShouldCallProperServices() {
    var userRequest = new UserInfo(123);
    var command = new CreateRepostCommand();
    command.setParent(123);

    var response = handler.handle(userRequest, command);

    verify(notifier).publish(any(CreateRepostEvent.class));
    assertEquals(123, response.getParent());
  }
}