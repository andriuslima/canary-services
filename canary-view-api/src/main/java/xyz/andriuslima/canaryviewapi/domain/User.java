package xyz.andriuslima.canaryviewapi.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class User {
    @Id
    private String id;
    private final Integer userId;
    private final String username;

    @CreatedDate
    private final Date joinedAt;
    @LastModifiedDate
    private Date updatedAt;
}
