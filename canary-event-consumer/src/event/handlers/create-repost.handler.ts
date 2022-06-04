import { Injectable, Logger } from '@nestjs/common';
import { NotifierService } from '../../notifier/notifier.service';
import { PrismaService } from '../../database/prisma.service';
import { CreateRepostEvent } from '../domain/repost-create-event';
import { Handler } from './handler';
import { PostCreationValidator } from '../validation/post-creation.validator';

@Injectable()
export class CreateRepostHandler implements Handler<CreateRepostEvent> {
  private readonly logger = new Logger(CreateRepostHandler.name);

  constructor(
    private readonly db: PrismaService,
    private readonly notifier: NotifierService,
    private readonly validator: PostCreationValidator,
  ) {}

  async handle(event: CreateRepostEvent) {
    this.logger.log('Handling create repost event', event);

    if (await this.validator.isNotValid(event)) {
      this.logger.log(`Sending event ${event.id} to DLT`);
      return this.notifier.sendToDLT(event);
    }

    const post = await this.db.createRepost(event.author, event.parent);

    this.logger.log(
      `Repost ${post.id} create in response of event ${event.id}`,
    );

    this.notifier.repostCreated({
      id: post.id,
      author: post.authorId,
      parent: post.parentId,
      createdAt: post.createdAt,
    });

    this.logger.log(`Repost ${post.id} notification sent`);
  }
}
