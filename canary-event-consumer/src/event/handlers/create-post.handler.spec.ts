import { Test, TestingModule } from '@nestjs/testing';
import { Post } from '@prisma/client';
import { mocked } from 'ts-jest/utils';
import { DatabaseModule } from '../../database/database.module';
import { PrismaService } from '../../database/prisma.service';
import { NotifierModule } from '../../notifier/notifier.module';
import { NotifierService } from '../../notifier/notifier.service';
import { CreatePostEvent } from '../domain/post-create-event';
import { PostCreationValidator } from '../validation/post-creation.validator';
import { CreatePostHandler } from './create-post.handler';

describe('CreatePostHandler', () => {
  const db = mocked(PrismaService.prototype);
  const notifier = mocked(NotifierService.prototype);
  const validator = mocked(PostCreationValidator.prototype);
  let handler: CreatePostHandler;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [DatabaseModule, NotifierModule],
      providers: [CreatePostHandler, PostCreationValidator, NotifierService],
    }).compile();

    handler = module.get<CreatePostHandler>(CreatePostHandler);
  });

  it('Should be defined', () => {
    expect(handler).toBeDefined();
  });

  test('Handle should call proper services', async () => {
    const event: CreatePostEvent = {
      author: 1,
      id: 'id',
      content: 'content',
      type: 'CreatePost',
    };

    const post: Post = {
      id: 1,
      authorId: 1,
      content: 'content',
      createdAt: new Date(),
      updatedAt: new Date(),
      parentId: 0,
    };

    validator.isNotValid = jest.fn().mockResolvedValue(false);
    notifier.sendToDLT = jest.fn().mockImplementation();
    db.createPost = jest.fn().mockResolvedValue(post);
    notifier.postCreated = jest.fn().mockImplementation();

    await handler.handle(event);

    expect(validator.isNotValid.mock.calls.length).toBe(1);
    expect(validator.isNotValid.mock.calls[0][0]).toStrictEqual(event);
    expect(notifier.sendToDLT.mock.calls.length).toBe(0);
    expect(db.createPost.mock.calls.length).toBe(1);
    expect(db.createPost.mock.calls[0][0]).toBe(event.author);
    expect(db.createPost.mock.calls[0][1]).toBe(event.content);
    expect(notifier.postCreated.mock.calls.length).toBe(1);
    expect(notifier.postCreated.mock.calls[0][0]).toStrictEqual({
      id: post.id,
      author: post.authorId,
      content: post.content,
      createdAt: post.createdAt,
    });
  });

  test('Handle should call proper services if post creation event if not valid', async () => {
    const event: CreatePostEvent = {
      author: 1,
      id: 'id',
      content: 'content',
      type: 'CreatePost',
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
