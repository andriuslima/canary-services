package xyz.andriuslima.canaryviewapi.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.andriuslima.canaryviewapi.api.auth.domain.UserInfo;
import xyz.andriuslima.canaryviewapi.application.services.PostService;
import xyz.andriuslima.canaryviewapi.application.views.PostView;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostService postService;

    @GetMapping()
    public List<PostView> listPosts(@RequestParam(name = "search", required = false) String search,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "10") int size) {
        return postService.listAllPosts(search, PageRequest.of(page, size));
    }

    @GetMapping("/follow")
    public List<PostView> listFollowPosts(UserInfo userInfo,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        return postService.listFollowingPosts(userInfo.getUserId(), PageRequest.of(page, size));
    }
}
