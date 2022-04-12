package xyz.andriuslima.canaryviewapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import xyz.andriuslima.canaryviewapi.application.exceptions.DomainException;

@RequiredArgsConstructor
@Service
public class LocalizedMessageTranslationService {
    private final MessageSource source;

    public String translateMessage(DomainException exception) {
        var locale = LocaleContextHolder.getLocale();
        return source.getMessage(exception.getMessageKey(), exception.getArguments(), locale);
    }
}
