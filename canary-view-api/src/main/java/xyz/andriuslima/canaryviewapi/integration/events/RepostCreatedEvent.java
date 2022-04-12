package xyz.andriuslima.canaryviewapi.integration.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepostCreatedEvent {
    private Integer id;
    private Integer author;
    private Integer parent;
    private Date createdAt;
}
