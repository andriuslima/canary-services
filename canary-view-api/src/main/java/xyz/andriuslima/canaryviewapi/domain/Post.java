package xyz.andriuslima.canaryviewapi.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import xyz.andriuslima.canaryviewapi.application.values.PostType;

import java.util.Date;

import static xyz.andriuslima.canaryviewapi.application.values.PostType.Post;

@Data
@Document
public class Post {
    @Id
    private String id;
    private final Integer postId;
    private final Integer authorId;

    private String content;
    private Integer parent;
    private PostType type = Post;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}
