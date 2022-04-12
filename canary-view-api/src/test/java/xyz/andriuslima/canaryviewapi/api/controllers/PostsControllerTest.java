package xyz.andriuslima.canaryviewapi.api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import xyz.andriuslima.canaryviewapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryviewapi.application.services.PostService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostsControllerTest {

    @Mock
    PostService postService;

    @InjectMocks
    private PostsController controller;

    @Test
    void listPostsShouldCallProperServices() {
        var search = "search";
        var page = PageRequest.of(0, 10);

        controller.listPosts(search, 0, 10);

        verify(postService).listAllPosts(search, page);
    }

    @Test
    void listFollowPostsShouldCallProperServices() {
        var userInfo = new UserInfo(1);
        var page = PageRequest.of(0, 10);

        controller.listFollowPosts(userInfo, 0, 10);

        verify(postService).listFollowingPosts(1, page);
    }
}
