package xyz.andriuslima.canaryviewapi.application.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import xyz.andriuslima.canaryviewapi.application.values.PostType;
import xyz.andriuslima.canaryviewapi.application.views.PostView;
import xyz.andriuslima.canaryviewapi.application.views.UserView;
import xyz.andriuslima.canaryviewapi.domain.Post;
import xyz.andriuslima.canaryviewapi.integration.events.PostCreatedEvent;
import xyz.andriuslima.canaryviewapi.integration.events.QuoteRepostCreatedEvent;
import xyz.andriuslima.canaryviewapi.integration.events.RepostCreatedEvent;
import xyz.andriuslima.canaryviewapi.repository.FollowRepository;
import xyz.andriuslima.canaryviewapi.repository.PostRepository;
import xyz.andriuslima.canaryviewapi.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public List<PostView> listFollowingPosts(Integer userId, PageRequest page) {
        var followingIds = followRepository.findAllFollowing(userId);
        followingIds.add(userId);
        var posts = postRepository.findAllByAuthors(followingIds, page);

        return toPostView(posts);
    }

    public List<PostView> listAllPosts(String search, PageRequest page) {
        if (isBlank(search)) {
            return toPostView(postRepository.findAllWithPage(page));
        } else {
            return toPostView(postRepository.search(search, page));
        }
    }

    public List<PostView> listUserPosts(Integer userId, PageRequest page) {
        var posts = postRepository.findByAuthor(userId, page);
        return toPostView(posts);
    }

    public void createPost(PostCreatedEvent event) {
        var post = new Post(event.getId(), event.getAuthor());
        post.setContent(event.getContent());
        post.setType(PostType.Post);
        var saved = postRepository.save(post);
        log.info("Post {} saved: {}", saved.getPostId(), saved.getId());

    }

    public void createRepost(RepostCreatedEvent event) {
        var post = new Post(event.getId(), event.getAuthor());
        post.setType(PostType.Repost);
        post.setParent(event.getParent());
        var saved = postRepository.save(post);
        log.info("Repost {} saved: {}", saved.getPostId(), saved.getId());
    }

    public void createQuoteRepost(QuoteRepostCreatedEvent event) {
        var post = new Post(event.getId(), event.getAuthor());
        post.setContent(event.getContent());
        post.setParent(event.getParent());
        post.setType(PostType.QuoteRepost);
        var saved = postRepository.save(post);
        log.info("Quote repost {} saved: {}", saved.getPostId(), saved.getId());
    }

    private List<PostView> toPostView(List<Post> posts) {
        var parentsIds = posts.stream()
                .map(Post::getParent)
                .filter(Objects::nonNull)
                .distinct()
                .collect(toList());

        var parents = postRepository.findAll(parentsIds)
                .stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(toMap(Post::getPostId, post -> post));

        var usersIds = Stream.of(parents.values(), posts)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(Post::getAuthorId)
                .distinct()
                .collect(toList());

        var users = userRepository.findAll(usersIds)
                .stream()
                .distinct()
                .map(user -> new UserView(user.getUserId(), user.getUsername(), user.getJoinedAt()))
                .collect(toMap(UserView::getId, user -> user));

        return posts.stream()
                .map(post -> new PostView(post.getPostId(),
                        users.getOrDefault(post.getAuthorId(), null),
                        post.getContent(),
                        parent(post, parents, users),
                        post.getType()))
                .collect(toList());
    }

    private PostView parent(Post post, Map<Integer, Post> parents, Map<Integer, UserView> users) {
        if (post.getType() == PostType.Post) {
            return null;
        }

        var parent = parents.get(post.getParent());

        var view = new PostView();
        view.setId(parent.getPostId());
        view.setAuthor(users.getOrDefault(parent.getAuthorId(), null));
        view.setContent(parent.getContent());
        view.setType(parent.getType());

        return view;
    }
}
