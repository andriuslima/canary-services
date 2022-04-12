package xyz.andriuslima.canaryviewapi.application.exceptions;

public class NotFoundException extends DomainException {
    public NotFoundException(String messageKey, String... arguments) {
        super(messageKey, arguments);
    }
}
