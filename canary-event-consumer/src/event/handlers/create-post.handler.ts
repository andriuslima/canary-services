import { Injectable, Logger } from '@nestjs/common';
import { PrismaService } from '../../database/prisma.service';
import { NotifierService } from '../../notifier/notifier.service';
import { CreatePostEvent } from '../domain/post-create-event';
import { PostCreationValidator } from '../validation/post-creation.validator';
import { Handler } from './handler';

@Injectable()
export class CreatePostHandler implements Handler<CreatePostEvent> {
  private readonly logger = new Logger(CreatePostHandler.name);

  constructor(
    private readonly db: PrismaService,
    private readonly notifier: NotifierService,
    private readonly validator: PostCreationValidator,
  ) {}

  async handle(event: CreatePostEvent) {
    this.logger.log(`Handling create post event ${event.id}`, event);

    if (await this.validator.isNotValid(event)) {
      this.logger.log(`Sending event ${event.id} to DLT`);
      return this.notifier.sendToDLT(event);
    }

    const post = await this.db.createPost(event.author, event.content);

    this.logger.log(`Post ${post.id} create in response of event ${event.id}`);

    this.notifier.postCreated({
      id: post.id,
      author: post.authorId,
      content: post.content,
      createdAt: post.createdAt,
    });

    this.logger.log(`Post ${post.id} notification sent`);
  }
}
