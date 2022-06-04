import { Injectable, Logger } from '@nestjs/common';
import { PrismaService } from '../../database/prisma.service';
import { IntegrationEvent } from '../domain/integration-event';

@Injectable()
export class PostCreationValidator {
  private readonly logger = new Logger(PostCreationValidator.name);
  private readonly POSTS_DAY_MAX = process.env.POSTS_DAY_MAX || 5;

  constructor(private readonly db: PrismaService) {}

  async isNotValid(event: IntegrationEvent) {
    return !(await this.isValid(event));
  }

  async isValid(event: IntegrationEvent) {
    const numberOfPosts = await this.db.countPostsOnDay(event.author);
    this.logger.log(
      `User created ${numberOfPosts} in the last 24h. Max is ${this.POSTS_DAY_MAX}`,
    );
    return numberOfPosts <= this.POSTS_DAY_MAX;
  }
}
