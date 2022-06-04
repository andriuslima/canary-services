import { Injectable, Logger } from '@nestjs/common';
import { IntegrationEvent } from './domain/integration-event';
import { CreatePostHandler } from './handlers/create-post.handler';
import { CreateQuoteRepostHandler } from './handlers/create-quote-repost.handler';
import { CreateRepostHandler } from './handlers/create-repost.handler';
import { Handler } from './handlers/handler';

@Injectable()
export class EventService {
  private readonly logger = new Logger(EventService.name);
  private readonly handlers: { [type: string]: Handler<IntegrationEvent> };

  constructor(
    private readonly createPostHandler: CreatePostHandler,
    private readonly createRepostHandler: CreateRepostHandler,
    private readonly createQuoteRepostHandler: CreateQuoteRepostHandler,
  ) {
    this.handlers = {
      CreatePost: createPostHandler,
      CreateRepost: createRepostHandler,
      CreateQuoteRepost: createQuoteRepostHandler,
    };
  }

  handleEvent(event: IntegrationEvent) {
    this.handlers[event.type].handle(event);
  }
}
