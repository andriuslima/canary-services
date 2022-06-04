import { Module } from '@nestjs/common';
import { DatabaseModule } from '../database/database.module';
import { NotifierModule } from '../notifier/notifier.module';
import { NotifierService } from '../notifier/notifier.service';
import { EventController } from './event.controller';
import { EventService } from './event.service';
import { CreatePostHandler } from './handlers/create-post.handler';
import { CreateQuoteRepostHandler } from './handlers/create-quote-repost.handler';
import { CreateRepostHandler } from './handlers/create-repost.handler';
import { PostCreationValidator } from './validation/post-creation.validator';

@Module({
  imports: [DatabaseModule, NotifierModule],
  controllers: [EventController],
  providers: [
    EventService,
    NotifierService,
    PostCreationValidator,
    CreatePostHandler,
    CreateRepostHandler,
    CreateQuoteRepostHandler,
  ],
})
export class EventModule {}
