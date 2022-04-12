package xyz.andriuslima.canaryviewapi.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.andriuslima.canaryviewapi.api.configuration.exception.ErrorBody;
import xyz.andriuslima.canaryviewapi.api.exceptions.UnauthorizedException;
import xyz.andriuslima.canaryviewapi.application.exceptions.BadRequestException;
import xyz.andriuslima.canaryviewapi.application.exceptions.DomainException;
import xyz.andriuslima.canaryviewapi.service.LocalizedMessageTranslationService;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static xyz.andriuslima.canaryviewapi.api.configuration.exception.ErrorBody.from;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class ExceptionMapperAdvice {

    private final LocalizedMessageTranslationService localizedMessageTranslationService;

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorBody handle(HttpServletRequest request, DomainException cause) {
        log.error("BAD_REQUEST", cause);
        var translated = localizedMessageTranslationService.translateMessage(cause);
        return from(translated, BAD_REQUEST.value());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ErrorBody handle(HttpServletRequest request, UnauthorizedException cause) {
        log.error("UNAUTHORIZED", cause);
        var translated = localizedMessageTranslationService.translateMessage(cause);
        return from(translated, UNAUTHORIZED.value());
    }
}
