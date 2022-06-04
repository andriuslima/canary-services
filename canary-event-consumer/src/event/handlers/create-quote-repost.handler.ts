import { Injectable, Logger } from '@nestjs/common';
import { NotifierService } from '../../notifier/notifier.service';
import { PrismaService } from '../../database/prisma.service';
import { CreateQuoteRepostEvent } from '../domain/quote-repost-create-event';
import { Handler } from './handler';
import { PostCreationValidator } from '../validation/post-creation.validator';

@Injectable()
export class CreateQuoteRepostHandler
  implements Handler<CreateQuoteRepostEvent>
{
  private readonly logger = new Logger(CreateQuoteRepostHandler.name);

  constructor(
    private readonly db: PrismaService,
    private readonly notifier: NotifierService,
    private readonly validator: PostCreationValidator,
  ) {}

  async handle(event: CreateQuoteRepostEvent) {
    this.logger.log('Handling create quote repost event', event);

    if (await this.validator.isNotValid(event)) {
      this.logger.log(`Sending event ${event.id} to DLT`);
      return this.notifier.sendToDLT(event);
    }

    const post = await this.db.createQuoteRepost(
      event.author,
      event.content,
      event.parent,
    );

    this.logger.log(
      `Quote repost ${post.id} create in response of event ${event.id}`,
    );

    this.notifier.quoteRepostCreated({
      id: post.id,
      author: post.authorId,
      parent: post.parentId,
      content: post.content,
      createdAt: post.createdAt,
    });

    this.logger.log(`Quote repost ${post.id} notification sent`);
  }
}
