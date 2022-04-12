package xyz.andriuslima.canaryviewapi.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.andriuslima.canaryviewapi.application.exceptions.NotFoundException;
import xyz.andriuslima.canaryviewapi.application.views.UserProfileView;
import xyz.andriuslima.canaryviewapi.repository.FollowRepository;
import xyz.andriuslima.canaryviewapi.repository.PostRepository;
import xyz.andriuslima.canaryviewapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    public UserProfileView getProfile(Integer author, Integer userId) {
        var user = userRepository.findUser(userId)
                .orElseThrow(() -> new NotFoundException("not_found_user", userId.toString()));

        var follows = followRepository.countFollows(userId);
        var followers = followRepository.countFollowers(userId);
        var isFollowing = followRepository.checkFollow(author, userId);
        var posts = postRepository.countPosts(userId);

        var view = new UserProfileView();
        view.setId(user.getUserId());
        view.setUsername(user.getUsername());
        view.setJoinedAt(user.getJoinedAt());
        view.setFollows(follows);
        view.setFollowers(followers);
        view.setIsFollowing(isFollowing);
        view.setNumberOfPosts(posts);

        return view;
    }
}
