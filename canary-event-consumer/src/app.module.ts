import { Module } from '@nestjs/common';
import { DatabaseModule } from './database/database.module';
import { EventModule } from './event/event.module';
import { NotifierModule } from './notifier/notifier.module';

@Module({
  imports: [EventModule, DatabaseModule, NotifierModule],
  controllers: [],
  providers: [],
})
export class AppModule {}
