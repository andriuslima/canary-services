package xyz.andriuslima.canaryviewapi.application.views;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserProfileView {
    private Integer id;
    private String username;
    private Date joinedAt;
    private Long numberOfPosts;
    private Long follows;
    private Long followers;
    private Boolean isFollowing;
}
