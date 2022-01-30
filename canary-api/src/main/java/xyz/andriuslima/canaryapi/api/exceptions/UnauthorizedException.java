package xyz.andriuslima.canaryapi.api.exceptions;

import xyz.andriuslima.canaryapi.application.exceptions.DomainException;

public class UnauthorizedException extends DomainException {
    public UnauthorizedException(String messageKey, String... arguments) {
        super(messageKey, arguments);
    }
}
