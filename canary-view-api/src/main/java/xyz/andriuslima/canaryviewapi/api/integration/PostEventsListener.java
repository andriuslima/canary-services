package xyz.andriuslima.canaryviewapi.api.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import xyz.andriuslima.canaryviewapi.application.services.PostService;
import xyz.andriuslima.canaryviewapi.integration.events.PostCreatedEvent;
import xyz.andriuslima.canaryviewapi.integration.events.QuoteRepostCreatedEvent;
import xyz.andriuslima.canaryviewapi.integration.events.RepostCreatedEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostEventsListener {

    private final PostService service;

    @KafkaListener(topics = "${canary.domain.event.topic.post-created}")
    public void listenPostCreatedEvent(Message<PostCreatedEvent> event) {
        log.info("post created event received: {}", event.getPayload());
        service.createPost(event.getPayload());
    }

    @KafkaListener(topics = "${canary.domain.event.topic.repost-created}")
    public void listenRepostCreatedEvent(Message<RepostCreatedEvent> event) {
        log.info("repost created event received: {}", event.getPayload());
        service.createRepost(event.getPayload());
    }

    @KafkaListener(topics = "${canary.domain.event.topic.quote-repost-created}")
    public void listenQuoteRepostCreatedEvent(Message<QuoteRepostCreatedEvent> event) {
        log.info("quote repost created event received: {}", event.getPayload());
        service.createQuoteRepost(event.getPayload());
    }
}
