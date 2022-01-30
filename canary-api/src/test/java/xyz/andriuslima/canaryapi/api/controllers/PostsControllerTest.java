package xyz.andriuslima.canaryapi.api.controllers;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.andriuslima.canaryapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryapi.application.commands.CreatePostCommand;
import xyz.andriuslima.canaryapi.application.commands.CreateQuoteRepostCommand;
import xyz.andriuslima.canaryapi.application.commands.CreateRepostCommand;
import xyz.andriuslima.canaryapi.application.handlers.CreatePostHandler;
import xyz.andriuslima.canaryapi.application.handlers.CreateQuoteRepostHandler;
import xyz.andriuslima.canaryapi.application.handlers.CreateRepostHandler;

@ExtendWith(MockitoExtension.class)
public class PostsControllerTest {

  @Mock
  CreatePostHandler createPostHandler;
  @Mock
  CreateRepostHandler createRepostHandler;
  @Mock
  CreateQuoteRepostHandler createQuoteRepostHandler;

  @InjectMocks
  private PostsController controller;

  @Test
  void createPostShouldCallProperServices() {
    var userRequest = new UserInfo(123);
    var command = new CreatePostCommand();
    command.setContent("content");

    controller.createPost(userRequest, command);

    verify(createPostHandler).handle(userRequest, command);
  }

  @Test
  void createRepostShouldCallProperServices() {
    var userRequest = new UserInfo(123);
    var command = new CreateRepostCommand();
    command.setParent(123);

    controller.createRepost(userRequest, command);

    verify(createRepostHandler).handle(userRequest, command);
  }

  @Test
  void createQuoteRepostShouldCallProperServices() {
    var userRequest = new UserInfo(123);
    var command = new CreateQuoteRepostCommand();
    command.setContent("content");
    command.setParent(123);

    controller.createQuoteRepost(userRequest, command);

    verify(createQuoteRepostHandler).handle(userRequest, command);
  }
}
