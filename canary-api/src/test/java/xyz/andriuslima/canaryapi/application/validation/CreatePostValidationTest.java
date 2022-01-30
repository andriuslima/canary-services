package xyz.andriuslima.canaryapi.application.validation;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.andriuslima.canaryapi.application.commands.CreatePostCommand;
import xyz.andriuslima.canaryapi.application.exceptions.BadRequestException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.yml")
public class CreatePostValidationTest {

  public Integer maxLen = 10;

  @InjectMocks
  CreatePostValidation validation;

  @Test
  public void shouldNotAllowContentToBeTooLong() {
    validation.maxLen = maxLen;

    var command = new CreatePostCommand();
    command.setContent("a".repeat(maxLen + 1));

    assertThrowsExactly(BadRequestException.class, () -> validation.validate(command));
  }
}