import { NestFactory } from '@nestjs/core';
import { MicroserviceOptions, Transport } from '@nestjs/microservices';
import { AppModule } from './app.module';

async function bootstrap() {
  const SERVER = process.env.KAFKA_BOOTSTRAP_SEVER || 'localhost:9092';
  const app = await NestFactory.createMicroservice<MicroserviceOptions>(
    AppModule,
    {
      transport: Transport.KAFKA,
      options: {
        client: {
          brokers: [SERVER],
        },
        consumer: {
          groupId: 'canary-event-consumer',
        },
      },
    },
  );
  await app.listen();
}
bootstrap();
