import {
  INestApplication,
  Injectable,
  Logger,
  OnModuleInit,
} from '@nestjs/common';
import { PrismaClient } from '@prisma/client';

@Injectable()
export class PrismaService extends PrismaClient implements OnModuleInit {
  private readonly logger = new Logger(PrismaService.name);

  async onModuleInit() {
    await this.$connect();
    this.logger.log('Connected to database');
  }

  async enableShutdownHooks(app: INestApplication) {
    this.$on('beforeExit', async () => {
      await app.close();
    });
  }

  async countPostsOnDay(author: number) {
    const yesterday = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
    const now = new Date();
    return await this.post.count({
      where: {
        authorId: author,
        createdAt: {
          lte: now,
          gte: yesterday,
        },
      },
    });
  }

  async createPost(author: number, content: string) {
    return await this.post.create({
      data: {
        authorId: +author,
        content: content,
      },
    });
  }

  async createRepost(author: number, parent: number) {
    return await this.post.create({
      data: {
        authorId: +author,
        parentId: +parent,
      },
    });
  }

  async createQuoteRepost(author: number, content: string, parent: number) {
    return await this.post.create({
      data: {
        authorId: +author,
        content: content,
        parentId: +parent,
      },
    });
  }

  async createUser(username: string) {
    return await this.user.create({
      data: {
        username: username,
      },
    });
  }

  async createFollow(follower: number, following: number) {
    return await this.follow.create({
      data: {
        follower: { connect: { id: +follower } },
        following: { connect: { id: +following } },
      },
    });
  }
}
