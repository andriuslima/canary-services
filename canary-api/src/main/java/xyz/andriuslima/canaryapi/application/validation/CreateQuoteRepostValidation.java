package xyz.andriuslima.canaryapi.application.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.andriuslima.canaryapi.application.commands.CreateQuoteRepostCommand;
import xyz.andriuslima.canaryapi.application.exceptions.BadRequestException;

@Component
public class CreateQuoteRepostValidation {

    @Value("${posterr.post.content.maxLen}")
    public Integer maxLen;

    public void validate(CreateQuoteRepostCommand command) {
        if (command.getContent().length() > maxLen) {
            throw new BadRequestException("bad_request_content_len_max", maxLen.toString());
        }
    }
}
