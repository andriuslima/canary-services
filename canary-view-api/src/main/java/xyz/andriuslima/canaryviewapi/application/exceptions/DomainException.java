package xyz.andriuslima.canaryviewapi.application.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DomainException extends RuntimeException {

    private String messageKey;
    private String[] arguments;

    public DomainException(String messageKey, String... arguments) {
        this.messageKey = messageKey;
        this.arguments = arguments;
    }

}
