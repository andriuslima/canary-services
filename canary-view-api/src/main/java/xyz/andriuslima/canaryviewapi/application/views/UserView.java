package xyz.andriuslima.canaryviewapi.application.views;

import lombok.Data;

import java.util.Date;

@Data
public class UserView {
    private final Integer id;
    private final String username;
    private final Date joinedAt;
}
