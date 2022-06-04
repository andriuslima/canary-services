import { Test, TestingModule } from '@nestjs/testing';
import { DatabaseModule } from '../database/database.module';
import { NotifierModule } from '../notifier/notifier.module';
import { NotifierService } from '../notifier/notifier.service';
import { EventController } from './event.controller';
import { EventService } from './event.service';
import { CreatePostHandler } from './handlers/create-post.handler';
import { CreateQuoteRepostHandler } from './handlers/create-quote-repost.handler';
import { CreateRepostHandler } from './handlers/create-repost.handler';
import { PostCreationValidator } from './validation/post-creation.validator';

describe('PostController', () => {
  let controller: EventController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [DatabaseModule, NotifierModule],
      controllers: [EventController],
      providers: [
        EventService,
        PostCreationValidator,
        CreatePostHandler,
        CreateRepostHandler,
        CreateQuoteRepostHandler,
        NotifierService,
      ],
    }).compile();

    controller = module.get<EventController>(EventController);
  });

  it('Should be defined', () => {
    expect(controller).toBeDefined();
  });
});
