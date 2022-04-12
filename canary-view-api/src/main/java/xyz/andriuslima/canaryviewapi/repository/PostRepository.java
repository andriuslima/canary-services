package xyz.andriuslima.canaryviewapi.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import xyz.andriuslima.canaryviewapi.domain.Post;

import java.util.List;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static xyz.andriuslima.canaryviewapi.application.values.PostType.Post;
import static xyz.andriuslima.canaryviewapi.application.values.PostType.QuoteRepost;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final MongoTemplate template;

    public Post save(Post post) {
        return template.save(post);
    }

    public List<Post> search(String search, PageRequest page) {
        var query = query(where("content").regex(compile(search, CASE_INSENSITIVE))
                .and("type").in(Post, QuoteRepost))
                .with(page);

        return template.find(query, Post.class);
    }

    public List<Post> findAllWithPage(PageRequest page) {
        var query = new Query().with(page);
        return template.find(query, Post.class);
    }

    public List<Post> findAll(List<Integer> ids) {
        var query = query(where("postId").in(ids));
        return template.find(query, Post.class);
    }

    public List<Post> findAllByAuthors(List<Integer> authors, PageRequest page) {
        var query = query(where("authorId").in(authors)).with(page);
        return template.find(query, Post.class);
    }

    public List<Post> findByAuthor(Integer author, PageRequest page) {
        var query = query(where("authorId").is(author))
                .with(page);

        return template.find(query, Post.class);
    }

    public Long countPosts(Integer userId) {
        var query = query(where("authorId").is(userId));
        return template.count(query, Post.class);
    }
}
