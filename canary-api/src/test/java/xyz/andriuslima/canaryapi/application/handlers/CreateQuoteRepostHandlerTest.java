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
import xyz.andriuslima.canaryapi.application.commands.CreateQuoteRepostCommand;
import xyz.andriuslima.canaryapi.application.validation.CreateQuoteRepostValidation;
import xyz.andriuslima.canaryapi.domain.events.CreateQuoteRepostEvent;

@ExtendWith(MockitoExtension.class)
class CreateQuoteRepostHandlerTest {

  @Mock
  CreateQuoteRepostValidation validation;
  @Mock
  EventNotifier notifier;

  @InjectMocks
  CreateQuoteRepostHandler handler;

  @Test
  void handleShouldCallProperServices() {
    var userRequest = new UserInfo(123);
    var command = new CreateQuoteRepostCommand();
    command.setContent("content");
    command.setParent(123);

    var response = handler.handle(userRequest, command);

    verify(validation).validate(command);
    verify(notifier).publish(any(CreateQuoteRepostEvent.class));
    assertEquals("content", response.getContent());
    assertEquals(123, response.getParent());
  }
}