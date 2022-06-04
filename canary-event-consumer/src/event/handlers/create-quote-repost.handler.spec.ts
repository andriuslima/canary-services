import { Test, TestingModule } from '@nestjs/testing';
import { Post } from '@prisma/client';
import { mocked } from 'ts-jest/utils';
import { DatabaseModule } from '../../database/database.module';
import { PrismaService } from '../../database/prisma.service';
import { NotifierModule } from '../../notifier/notifier.module';
import { NotifierService } from '../../notifier/notifier.service';
import { CreateQuoteRepostEvent } from '../domain/quote-repost-create-event';
import { PostCreationValidator } from '../validation/post-creation.validator';
import { CreateQuoteRepostHandler } from './create-quote-repost.handler';

describe('CreateQuoteRepostHandler', () => {
  const db = mocked(PrismaService.prototype);
  const notifier = mocked(NotifierService.prototype);
  const validator = mocked(PostCreationValidator.prototype);
  let handler: CreateQuoteRepostHandler;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [DatabaseModule, NotifierModule],
      providers: [
        CreateQuoteRepostHandler,
        PostCreationValidator,
        NotifierService,
      ],
    }).compile();

    handler = module.get<CreateQuoteRepostHandler>(CreateQuoteRepostHandler);
  });

  it('Should be defined', () => {
    expect(handler).toBeDefined();
  });

  test('Handle should call proper services', async () => {
    const event: CreateQuoteRepostEvent = {
      author: 1,
      content: 'some content',
      id: 'id',
      parent: 9,
      type: 'CreateQuoteRepost',
    };

    const post: Post = {
      id: 1,
      authorId: 1,
      content: 'some content',
      createdAt: new Date(),
      updatedAt: new Date(),
      parentId: 9,
    };

    validator.isNotValid = jest.fn().mockResolvedValue(false);
    notifier.sendToDLT = jest.fn().mockImplementation();
    db.createQuoteRepost = jest.fn().mockResolvedValue(post);
    notifier.quoteRepostCreated = jest.fn().mockImplementation();

    await handler.handle(event);

    expect(validator.isNotValid.mock.calls.length).toBe(1);
    expect(validator.isNotValid.mock.calls[0][0]).toStrictEqual(event);
    expect(notifier.sendToDLT.mock.calls.length).toBe(0);
    expect(db.createQuoteRepost.mock.calls.length).toBe(1);
    expect(db.createQuoteRepost.mock.calls[0][0]).toBe(event.author);
    expect(db.createQuoteRepost.mock.calls[0][1]).toBe(event.content);
    expect(db.createQuoteRepost.mock.calls[0][2]).toBe(event.parent);
    expect(notifier.quoteRepostCreated.mock.calls.length).toBe(1);
    expect(notifier.quoteRepostCreated.mock.calls[0][0]).toStrictEqual({
      id: post.id,
      author: post.authorId,
      parent: post.parentId,
      content: post.content,
      createdAt: post.createdAt,
    });
  });

  test('Handle should call proper services if quote repost creation event if not valid', async () => {
    const event: CreateQuoteRepostEvent = {
      author: 1,
      content: 'some content',
      id: 'id',
      parent: 9,
      type: 'CreateQuoteRepost',
    };

    validator.isNotValid = jest.fn().mockResolvedValue(true);
    notifier.sendToDLT = jest.fn().mockImplementation();

    await handler.handle(event);

    expect(validator.isNotValid.mock.calls.length).toBe(1);
    expect(validator.isNotValid.mock.calls[0][0]).toStrictEqual(event);
    expect(notifier.sendToDLT.mock.calls.length).toBe(1);
    expect(notifier.sendToDLT.mock.calls[0][0]).toStrictEqual(event);
  });
});
