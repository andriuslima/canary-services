package xyz.andriuslima.canaryviewapi.api.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.support.GenericMessage;
import xyz.andriuslima.canaryviewapi.application.services.PostService;
import xyz.andriuslima.canaryviewapi.integration.events.PostCreatedEvent;
import xyz.andriuslima.canaryviewapi.integration.events.QuoteRepostCreatedEvent;
import xyz.andriuslima.canaryviewapi.integration.events.RepostCreatedEvent;

import java.util.Date;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostEventsListenerTest {

    @Mock
    PostService service;

    @InjectMocks
    PostEventsListener listener;

    @Test
    void listenPostCreatedEventShouldCallProperServices() {
        var event = new PostCreatedEvent();
        event.setId(1);
        event.setAuthor(1);
        event.setContent("Some content");
        event.setCreatedAt(new Date());
        var message = new GenericMessage<>(event);

        listener.listenPostCreatedEvent(message);

        verify(service).createPost(event);
    }

    @Test
    void listenRepostCreatedEvent() {
        var event = new RepostCreatedEvent();
        event.setId(1);
        event.setAuthor(1);
        event.setParent(1);
        event.setCreatedAt(new Date());
        var message = new GenericMessage<>(event);

        listener.listenRepostCreatedEvent(message);

        verify(service).createRepost(event);
    }

    @Test
    void listenQuoteRepostCreatedEvent() {
        var event = new QuoteRepostCreatedEvent();
        event.setId(1);
        event.setAuthor(1);
        event.setContent("Some content");
        event.setParent(1);
        event.setCreatedAt(new Date());
        var message = new GenericMessage<>(event);

        listener.listenQuoteRepostCreatedEvent(message);

        verify(service).createQuoteRepost(event);
    }
}