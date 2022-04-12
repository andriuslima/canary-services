package xyz.andriuslima.canaryviewapi.application.exceptions;

public class BadRequestException extends DomainException {

    public BadRequestException(String messageKey, String... arguments) {
        super(messageKey, arguments);
    }
}
