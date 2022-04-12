package xyz.andriuslima.canaryviewapi.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.andriuslima.canaryviewapi.application.exceptions.NotFoundException;
import xyz.andriuslima.canaryviewapi.domain.User;
import xyz.andriuslima.canaryviewapi.repository.FollowRepository;
import xyz.andriuslima.canaryviewapi.repository.PostRepository;
import xyz.andriuslima.canaryviewapi.repository.UserRepository;

import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    FollowRepository followRepository;

    @InjectMocks
    UserService userService;

    @Test
    void getProfileShouldCallProperServices() {
        var author = 1;
        var userId = 9;
        var user = mock(User.class);

        given(userRepository.findUser(userId)).willReturn(of(user));

        userService.getProfile(author, userId);

        verify(userRepository).findUser(userId);
        verify(followRepository).countFollows(userId);
        verify(followRepository).countFollowers(userId);
        verify(followRepository).checkFollow(author, userId);
        verify(postRepository).countPosts(userId);
    }

    @Test
    void getProfileShouldThrowNotFoundExceptionIfUserDoNotexists() {
        var author = 1;
        var userId = 9;

        given(userRepository.findUser(userId)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getProfile(author, userId));
    }
}