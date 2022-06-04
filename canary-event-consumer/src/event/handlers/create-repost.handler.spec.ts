import { Test, TestingModule } from '@nestjs/testing';
import { Post } from '@prisma/client';
import { mocked } from 'ts-jest/utils';
import { DatabaseModule } from '../../database/database.module';
import { PrismaService } from '../../database/prisma.service';
import { NotifierModule } from '../../notifier/notifier.module';
import { NotifierService } from '../../notifier/notifier.service';
import { CreateRepostEvent } from '../domain/repost-create-event';
import { PostCreationValidator } from '../validation/post-creation.validator';
import { CreateRepostHandler } from './create-repost.handler';

describe('CreateRepostHandler', () => {
  const db = mocked(PrismaService.prototype);
  const notifier = mocked(NotifierService.prototype);
  const validator = mocked(PostCreationValidator.prototype);
  let handler: CreateRepostHandler;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [DatabaseModule, NotifierModule],
      providers: [CreateRepostHandler, PostCreationValidator, NotifierService],
    }).compile();

    handler = module.get<CreateRepostHandler>(CreateRepostHandler);
  });

  it('Should be defined', () => {
    expect(handler).toBeDefined();
  });

  test('Handle should call proper services', async () => {
    const event: CreateRepostEvent = {
      author: 1,
      id: 'id',
      parent: 9,
      type: 'CreateRepost',
    };

    const post: Post = {
      id: 1,
      authorId: 1,
      content: undefined,
      createdAt: new Date(),
      updatedAt: new Date(),
      parentId: 9,
    };

    validator.isNotValid = jest.fn().mockResolvedValue(false);
    notifier.sendToDLT = jest.fn().mockImplementation();
    db.createRepost = jest.fn().mockResolvedValue(post);
    notifier.repostCreated = jest.fn().mockImplementation();

    await handler.handle(event);

    expect(validator.isNotValid.mock.calls.length).toBe(1);
    expect(validator.isNotValid.mock.calls[0][0]).toStrictEqual(event);
    expect(notifier.sendToDLT.mock.calls.length).toBe(0);
    expect(db.createRepost.mock.calls.length).toBe(1);
    expect(db.createRepost.mock.calls[0][0]).toBe(event.author);
    expect(db.createRepost.mock.calls[0][1]).toBe(event.parent);
    expect(notifier.repostCreated.mock.calls.length).toBe(1);
    expect(notifier.repostCreated.mock.calls[0][0]).toStrictEqual({
      id: post.id,
      author: post.authorId,
      parent: post.parentId,
      createdAt: post.createdAt,
    });
  });

  test('Handle should call proper services if repost creation event if not valid', async () => {
    const event: CreateRepostEvent = {
      author: 1,
      id: 'id',
      parent: 9,
      type: 'CreateRepost',
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
