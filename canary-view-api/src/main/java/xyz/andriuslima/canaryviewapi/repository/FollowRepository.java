
package xyz.andriuslima.canaryviewapi.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import xyz.andriuslima.canaryviewapi.domain.Follow;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class FollowRepository {
    private final MongoTemplate template;

    public List<Integer> findAllFollowing(Integer id) {
        var query = query(where("follower").is(id));
        return template.find(query, Follow.class)
                .stream()
                .map(Follow::getFollowing)
                .collect(toList());
    }

    public Long countFollows(Integer userId) {
        var query = query(where("follower").is(userId));
        return template.count(query, Follow.class);
    }

    public Long countFollowers(Integer userId) {
        var query = query(where("following").is(userId));
        return template.count(query, Follow.class);
    }

    public Boolean checkFollow(Integer author, Integer userId) {
        var query = query(where("follower").is(author)
                .and("following").is(userId));
        return template.exists(query, Follow.class);
    }
}
