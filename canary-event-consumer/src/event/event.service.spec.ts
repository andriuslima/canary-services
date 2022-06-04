import { Test, TestingModule } from '@nestjs/testing';
import { NotifierModule } from '../notifier/notifier.module';
import { NotifierService } from '../notifier/notifier.service';
import { DatabaseModule } from '../database/database.module';
import { CreatePostEvent } from './domain/post-create-event';
import { CreateQuoteRepostEvent } from './domain/quote-repost-create-event';
import { CreateRepostEvent } from './domain/repost-create-event';
import { EventService } from './event.service';
import { CreatePostHandler } from './handlers/create-post.handler';
import { CreateQuoteRepostHandler } from './handlers/create-quote-repost.handler';
import { CreateRepostHandler } from './handlers/create-repost.handler';
import { PostCreationValidator } from './validation/post-creation.validator';

describe('PostService', () => {
  let service: EventService;
  let createPostHandler: CreatePostHandler;
  let createRepostHandler: CreateRepostHandler;
  let createQuoteRepostHandler: CreateQuoteRepostHandler;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [DatabaseModule, NotifierModule],
      providers: [
        EventService,
        PostCreationValidator,
        CreatePostHandler,
        CreateRepostHandler,
        CreateQuoteRepostHandler,
        NotifierService,
      ],
    }).compile();

    service = module.get<EventService>(EventService);
    createPostHandler = module.get<CreatePostHandler>(CreatePostHandler);
    createRepostHandler = module.get<CreateRepostHandler>(CreateRepostHandler);
    createQuoteRepostHandler = module.get<CreateQuoteRepostHandler>(
      CreateQuoteRepostHandler,
    );
  });

  it('Should be defined', () => {
    expect(service).toBeDefined();
  });

  test('CreatePost event should call proper services', async () => {
    const event: CreatePostEvent = {
      id: 'random-id',
      type: 'CreatePost',
      author: 1,
      content: 'some content',
    };

    const mockedHandler = jest
      .spyOn(createPostHandler, 'handle')
      .mockImplementation();

    service.handleEvent(event);

    expect(mockedHandler.mock.calls.length).toBe(1);
    expect(mockedHandler.mock.calls[0][0]).toBe(event);
  });

  test('CreateRepost event should call proper services', async () => {
    const event: CreateRepostEvent = {
      id: 'random-id',
      type: 'CreateRepost',
      author: 1,
      parent: 1,
    };

    const mockedHandler = jest
      .spyOn(createRepostHandler, 'handle')
      .mockImplementation();

    service.handleEvent(event);

    expect(mockedHandler.mock.calls.length).toBe(1);
    expect(mockedHandler.mock.calls[0][0]).toBe(event);
  });

  test('CreateQuoteRepost event should call proper services', async () => {
    const event: CreateQuoteRepostEvent = {
      id: 'random-id',
      type: 'CreateQuoteRepost',
      author: 1,
      parent: 1,
      content: 'some content',
    };

    const mockedHandler = jest
      .spyOn(createQuoteRepostHandler, 'handle')
      .mockImplementation();

    service.handleEvent(event);

    expect(mockedHandler.mock.calls.length).toBe(1);
    expect(mockedHandler.mock.calls[0][0]).toBe(event);
  });
});
