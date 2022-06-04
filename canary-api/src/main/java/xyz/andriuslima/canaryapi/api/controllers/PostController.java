package xyz.andriuslima.canaryapi.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.andriuslima.canaryapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryapi.application.commands.CreatePostCommand;
import xyz.andriuslima.canaryapi.application.commands.CreateQuoteRepostCommand;
import xyz.andriuslima.canaryapi.application.commands.CreateRepostCommand;
import xyz.andriuslima.canaryapi.application.handlers.CreatePostHandler;
import xyz.andriuslima.canaryapi.application.handlers.CreateQuoteRepostHandler;
import xyz.andriuslima.canaryapi.application.handlers.CreateRepostHandler;
import xyz.andriuslima.canaryapi.application.response.CreatePostResponse;
import xyz.andriuslima.canaryapi.application.response.CreateQuoteRepostResponse;
import xyz.andriuslima.canaryapi.application.response.CreateRepostResponse;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final CreatePostHandler createPostHandler;
    private final CreateRepostHandler createRepostHandler;
    private final CreateQuoteRepostHandler createQuoteRepostHandler;

    @PostMapping()
    @ResponseStatus(CREATED)
    public CreatePostResponse createPost(UserInfo userInfo,
                                         @RequestBody CreatePostCommand command) {
        log.info("Post creation request received from user {}", userInfo.getUserId());
        return createPostHandler.handle(userInfo, command);
    }

    @PostMapping("/repost")
    @ResponseStatus(CREATED)
    public CreateRepostResponse createRepost(UserInfo userInfo,
                                             @RequestBody CreateRepostCommand command) {
        log.info("Repost creation request received from user {}", userInfo.getUserId());
        return createRepostHandler.handle(userInfo, command);
    }

    @PostMapping("/quote")
    @ResponseStatus(CREATED)
    public CreateQuoteRepostResponse createQuoteRepost(UserInfo userInfo,
                                                       @RequestBody CreateQuoteRepostCommand command) {
        log.info("Quote post creation request received from user {}", userInfo.getUserId());
        return createQuoteRepostHandler.handle(userInfo, command);
    }
}
