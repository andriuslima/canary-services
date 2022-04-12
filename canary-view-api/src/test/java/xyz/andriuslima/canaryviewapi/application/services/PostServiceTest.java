package xyz.andriuslima.canaryviewapi.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import xyz.andriuslima.canaryviewapi.application.values.PostType;
import xyz.andriuslima.canaryviewapi.domain.Post;
import xyz.andriuslima.canaryviewapi.integration.events.PostCreatedEvent;
import xyz.andriuslima.canaryviewapi.integration.events.QuoteRepostCreatedEvent;
import xyz.andriuslima.canaryviewapi.integration.events.RepostCreatedEvent;
import xyz.andriuslima.canaryviewapi.repository.FollowRepository;
import xyz.andriuslima.canaryviewapi.repository.PostRepository;
import xyz.andriuslima.canaryviewapi.repository.UserRepository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.List.of;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    FollowRepository followRepository;

    @Mock
    PostRepository repository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    PostService service;

    @Test
    void listFollowingPostsShouldCallProperServices() {
        var userId = 1;
        var following = new LinkedList<>(List.of(3, 6, 7));
        var page = PageRequest.of(0, 10);
        var post01 = new Post(1, 3);
        var post02 = new Post(2, 6);
        var post03 = new Post(3, 7);

        given(followRepository.findAllFollowing(userId)).willReturn(following);
        given(repository.findAllByAuthors(List.of(3, 6, 7, 1), page)).willReturn(of(post01, post02, post03));

        service.listFollowingPosts(userId, page);

        verify(followRepository).findAllFollowing(userId);
        verify(repository).findAllByAuthors(List.of(3, 6, 7, 1), page);
    }

    @Test
    void listAllPostsWithSearchShouldCallProperServices() {
        var search = "search";
        var page = PageRequest.of(0, 10);
        var post01 = new Post(1, 1);
        var post02 = new Post(2, 2);
        var post03 = new Post(3, 3);

        var postChild = new Post(4, 2);
        var postParent = new Post(5, 1);
        postChild.setParent(postParent.getPostId());

        given(repository.search(search, page)).willReturn(of(post01, post02, post03, postChild));
        given(repository.findAll(List.of(postParent.getPostId()))).willReturn(emptyList());

        service.listAllPosts(search, page);

        verify(repository, never()).findAllWithPage(page);
        verify(repository).search(search, page);
        verify(userRepository).findAll(List.of(1, 2, 3));
    }

    @Test
    void listAllPostsWithNoSearchShouldCallProperServices() {
        String search = "";
        var page = PageRequest.of(0, 10);
        var post01 = new Post(1, 1);
        var post02 = new Post(2, 2);
        var post03 = new Post(3, 3);

        given(repository.findAllWithPage(page)).willReturn(of(post01, post02, post03));

        service.listAllPosts(search, page);

        verify(repository).findAllWithPage(page);
        verify(repository, never()).search(search, page);
        verify(userRepository).findAll(List.of(1, 2, 3));
    }

    @Test
    void listUserPostsShouldCallProperServices() {
        var userId = 1;
        var page = PageRequest.of(0, 10);
        var post01 = new Post(1, 1);
        var post02 = new Post(2, 1);
        var post03 = new Post(3, 1);

        given(repository.findByAuthor(1, page)).willReturn(of(post01, post02, post03));

        service.listUserPosts(userId, page);

        verify(repository).findByAuthor(userId, page);
    }

    @Test
    void createPostShouldCallProperServices() {
        var event = new PostCreatedEvent();
        event.setId(1);
        event.setAuthor(1);
        event.setContent("Some content");
        event.setCreatedAt(new Date());

        var post = new Post(1, 1);
        post.setContent(event.getContent());
        post.setType(PostType.Post);

        var saved = new Post(1, 1);
        post.setContent(event.getContent());
        post.setType(PostType.Post);

        given(repository.save(post)).willReturn(saved);

        service.createPost(event);

        verify(repository).save(post);
    }

    @Test
    void createRepostShouldCallProperServices() {
        var event = new RepostCreatedEvent();
        event.setId(1);
        event.setAuthor(1);
        event.setParent(1);
        event.setCreatedAt(new Date());

        var post = new Post(1, 1);
        post.setParent(1);
        post.setType(PostType.Repost);

        var saved = new Post(1, 1);
        post.setParent(1);
        post.setType(PostType.Repost);

        given(repository.save(post)).willReturn(saved);

        service.createRepost(event);

        verify(repository).save(post);
    }

    @Test
    void createQuoteRepost() {
        var event = new QuoteRepostCreatedEvent();
        event.setId(1);
        event.setAuthor(1);
        event.setContent("Some content");
        event.setParent(1);
        event.setCreatedAt(new Date());

        var post = new Post(1, 1);
        post.setContent(event.getContent());
        post.setParent(1);
        post.setType(PostType.QuoteRepost);

        var saved = new Post(1, 1);
        post.setContent(event.getContent());
        post.setParent(1);
        post.setType(PostType.QuoteRepost);

        given(repository.save(post)).willReturn(saved);

        service.createQuoteRepost(event);

        verify(repository).save(post);
    }
}