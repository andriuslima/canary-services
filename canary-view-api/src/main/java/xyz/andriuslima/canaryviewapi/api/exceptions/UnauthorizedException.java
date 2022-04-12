package xyz.andriuslima.canaryviewapi.api.exceptions;

import xyz.andriuslima.canaryviewapi.application.exceptions.DomainException;

public class UnauthorizedException extends DomainException {
    public UnauthorizedException(String messageKey, String... arguments) {
        super(messageKey, arguments);
    }
}
