import { Injectable } from '@nestjs/common';
import { Client, ClientKafka, Transport } from '@nestjs/microservices';
import { IntegrationEvent } from '../event/domain/integration-event';
import { PostCreated } from './domain/post-created';
import { QuoteRepostCreated } from './domain/quote-repost-created';
import { RepostCreated } from './domain/repost-created';

@Injectable()
export class NotifierService {
  @Client({
    transport: Transport.KAFKA,
    options: {
      client: {
        clientId: 'canary-event-consumer-notifier',
        brokers: [process.env.KAFKA_BOOTSTRAP_SEVER || 'localhost:9092'],
      },
      producer: {
        allowAutoTopicCreation: true,
      },
    },
  })
  client: ClientKafka;

  postCreated(event: PostCreated) {
    this.client.emit('PostCreated', { key: event.id, value: event });
  }

  repostCreated(event: RepostCreated) {
    this.client.emit('RepostCreated', { key: event.id, value: event });
  }

  quoteRepostCreated(event: QuoteRepostCreated) {
    this.client.emit('QuoteRepostCreated', { key: event.id, value: event });
  }

  sendToDLT(event: IntegrationEvent) {
    this.client.emit('CanaryEventDLT', { key: event.id, value: event });
  }
}
