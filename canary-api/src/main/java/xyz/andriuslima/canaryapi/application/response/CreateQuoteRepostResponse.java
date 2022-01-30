package xyz.andriuslima.canaryapi.application.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateQuoteRepostResponse {
    private String content;
    private Integer parent;
}
