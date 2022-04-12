package xyz.andriuslima.canaryviewapi.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.andriuslima.canaryviewapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryviewapi.application.services.PostService;
import xyz.andriuslima.canaryviewapi.application.services.UserService;
import xyz.andriuslima.canaryviewapi.application.views.PostView;
import xyz.andriuslima.canaryviewapi.application.views.UserProfileView;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/{userId}/profile")
    public UserProfileView getUserProfile(UserInfo userInfo, @PathVariable(name = "userId") Integer userId) {
        return userService.getProfile(userInfo.getUserId(), userId);
    }

    @GetMapping("/{userId}/posts")
    public List<PostView> listUserPosts(UserInfo userInfo,
                                        @PathVariable(name = "userId") Integer userId,
                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "10") int size) {

        if (!userInfo.getUserId().equals(userId)) {
            log.info("User {} request to list user {} posts", userInfo.getUserId(), userId);
        }

        return postService.listUserPosts(userId, of(page, size));
    }
}
