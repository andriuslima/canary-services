package xyz.andriuslima.canaryviewapi.application.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.andriuslima.canaryviewapi.application.values.PostType;

import static xyz.andriuslima.canaryviewapi.application.values.PostType.Post;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostView {
    private Integer id;
    private UserView author;
    private String content;
    private PostView parent;

    private PostType type = Post;
}
