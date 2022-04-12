
package xyz.andriuslima.canaryviewapi.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import xyz.andriuslima.canaryviewapi.domain.User;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final MongoTemplate template;

    public List<User> findAll(List<Integer> ids) {
        var query = query(where("userId").in(ids));
        return template.find(query, User.class);
    }

    public Optional<User> findUser(Integer id) {
        return ofNullable(template.findOne(query(where("userId").is(id)), User.class));
    }
}
