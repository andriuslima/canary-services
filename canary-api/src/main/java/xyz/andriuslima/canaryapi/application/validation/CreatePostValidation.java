package xyz.andriuslima.canaryapi.application.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.andriuslima.canaryapi.application.commands.CreatePostCommand;
import xyz.andriuslima.canaryapi.application.exceptions.BadRequestException;

@Component
public class CreatePostValidation {

    @Value("${posterr.post.content.maxLen}")
    public Integer maxLen;

    public void validate(CreatePostCommand command) {
        if (command.getContent().length() > maxLen) {
            throw new BadRequestException("bad_request_content_len_max", maxLen.toString());
        }
    }
}
