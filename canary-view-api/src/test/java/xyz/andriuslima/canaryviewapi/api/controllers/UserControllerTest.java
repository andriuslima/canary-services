package xyz.andriuslima.canaryviewapi.api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import xyz.andriuslima.canaryviewapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryviewapi.application.services.PostService;
import xyz.andriuslima.canaryviewapi.application.services.UserService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    PostService postService;

    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;

    @Test
    void listUserPostsShouldCallProperService() {
        var userInfo = new UserInfo(1);
        var userId = 9;
        var page = PageRequest.of(0, 10);

        controller.listUserPosts(userInfo, 9, 0, 10);

        verify(postService).listUserPosts(userId, page);
    }

    @Test
    void getUserProfileShouldCallProperService() {
        var userInfo = new UserInfo(1);
        var userId = 9;

        controller.getUserProfile(userInfo, 9);

        verify(userService).getProfile(userInfo.getUserId(), userId);
    }
}